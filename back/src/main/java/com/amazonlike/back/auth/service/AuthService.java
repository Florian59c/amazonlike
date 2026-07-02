package com.amazonlike.back.auth.service;

import com.amazonlike.back.auth.dto.LoginDto;
import com.amazonlike.back.auth.dto.RegisterDto;
import com.amazonlike.back.auth.entity.PasswordResetToken;
import com.amazonlike.back.auth.jwt.JwtService;
import com.amazonlike.back.auth.repository.PasswordResetTokenRepository;
import com.amazonlike.back.config.CookieProperties;
import com.amazonlike.back.config.JwtProperties;
import com.amazonlike.back.mail.service.MailService;
import com.amazonlike.back.user.entity.User;
import com.amazonlike.back.user.repository.UserRepository;
import com.amazonlike.back.user.role.Role;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final CookieProperties cookieProperties;
  private final JwtProperties jwtProperties;
  private final PasswordResetTokenRepository passwordResetTokenRepository;
  private final MailService mailService;

  public AuthService(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtService jwtService,
      CookieProperties cookieProperties,
      JwtProperties jwtProperties,
      PasswordResetTokenRepository passwordResetTokenRepository,
      MailService mailService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.cookieProperties = cookieProperties;
    this.jwtProperties = jwtProperties;
    this.passwordResetTokenRepository = passwordResetTokenRepository;
    this.mailService = mailService;
  }

  public void register(RegisterDto request) {

    if (!request.getPassword().equals(request.getConfirmPassword())) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Les mots de passe ne correspondent pas");
    }

    User existing = userRepository.findByEmail(request.getEmail()).orElse(null);

    if (existing != null) {

      boolean accountDeleted = existing.getDeletedAt() != null;

      if (!accountDeleted) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Cette adresse email est déjà utilisée");
      }

      existing.setFirstName(request.getFirstName());
      existing.setLastName(request.getLastName());
      existing.setPassword(passwordEncoder.encode(request.getPassword()));
      existing.setEnabled(true);
      existing.setLocked(false);
      existing.setDeletedAt(null);
      existing.setTokenVersion(UUID.randomUUID());

      userRepository.save(existing);
      return;
    }

    User user = new User();

    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setRole(Role.USER);
    user.setEnabled(true);
    user.setLocked(false);
    user.setTokenVersion(UUID.randomUUID());

    userRepository.save(user);
  }

  public void login(LoginDto request, HttpServletResponse response) {

    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.UNAUTHORIZED,
            "Identifiants incorrects"));

    if (!user.isEnabled() || user.isLocked() || user.getDeletedAt() != null) {
      throw new ResponseStatusException(
          HttpStatus.FORBIDDEN,
          "Compte désactivé");
    }

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new ResponseStatusException(
          HttpStatus.UNAUTHORIZED,
          "Identifiants incorrects");
    }

    // token invalidation system
    UUID tokenVersion = UUID.randomUUID();
    user.setTokenVersion(tokenVersion);
    userRepository.save(user);

    String jwt = jwtService.generateToken(user.getId(), tokenVersion);

    ResponseCookie cookie = ResponseCookie.from(cookieProperties.getName(), jwt)
        .httpOnly(true)
        .secure(cookieProperties.isSecure())
        .path("/")
        .maxAge(cookieProperties.getMaxAge())
        .sameSite("Strict")
        .build();

    response.addHeader("Set-Cookie", cookie.toString());
  }

  public void logout(User user, HttpServletResponse response) {

    if (user != null) {
      user.setTokenVersion(UUID.randomUUID());
      userRepository.save(user);
    }

    ResponseCookie cookie = ResponseCookie.from(cookieProperties.getName(), "")
        .httpOnly(true)
        .secure(cookieProperties.isSecure())
        .path("/")
        .maxAge(0) // suppression immédiate du cookie
        .sameSite("Strict")
        .build();

    response.addHeader("Set-Cookie", cookie.toString());
  }

  public void forgotPassword(String email) {

    User user = userRepository.findByEmail(email)
        .orElse(null);

    if (user == null) {
      return; // silent fail : on ne retourne rien si user null par mesure de sécurité pour
              // eviter d'informer sur l'existence de l'email en base de données
    }

    passwordResetTokenRepository.deleteByUser(user); // suppression des anciens tokens

    String token = UUID.randomUUID().toString();

    PasswordResetToken resetToken = new PasswordResetToken();
    resetToken.setToken(token);
    resetToken.setUser(user);
    resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));

    passwordResetTokenRepository.save(resetToken);

    mailService.sendResetPasswordMail(user.getEmail(), token);
  }
}