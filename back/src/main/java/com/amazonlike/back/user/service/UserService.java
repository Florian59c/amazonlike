package com.amazonlike.back.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.amazonlike.back.mail.service.MailService;
import com.amazonlike.back.user.dto.UpdateEmailRequestDto;
import com.amazonlike.back.user.dto.UpdateProfileDto;
import com.amazonlike.back.user.dto.UserProfileDto;
import com.amazonlike.back.user.entity.EmailUpdateToken;
import com.amazonlike.back.user.entity.User;
import com.amazonlike.back.user.repository.EmailUpdateTokenRepository;
import com.amazonlike.back.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final EmailUpdateTokenRepository emailUpdateTokenRepository;
  private final MailService mailService;

  public UserService(UserRepository userRepository, EmailUpdateTokenRepository emailUpdateTokenRepository,
      MailService mailService) {
    this.userRepository = userRepository;
    this.emailUpdateTokenRepository = emailUpdateTokenRepository;
    this.mailService = mailService;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getUserById(UUID id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Utilisateur introuvable"));
  }

  public UserProfileDto getCurrentUserProfile(User user) {
    return new UserProfileDto(
        user.getFirstName(),
        user.getLastName());
  }

  public User updateProfile(User user, UpdateProfileDto request) {

    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());

    return userRepository.save(user);
  }

  public void deleteCurrentUser(User user) {

    user.setEnabled(false);
    user.setLocked(true);
    user.setDeletedAt(LocalDateTime.now());
    user.setTokenVersion(UUID.randomUUID());

    userRepository.save(user);
  }

  @Transactional
  public void requestEmailUpdate(
      User user,
      UpdateEmailRequestDto request) {

    String newEmail = request.getNewEmail();

    if (user.getEmail().equals(newEmail)) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Le nouvel e-mail doit être différent");
    }

    if (userRepository.existsByEmail(newEmail)) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT,
          "Cette adresse e-mail est déjà utilisée");
    }

    emailUpdateTokenRepository.deleteAllByUser(user);

    EmailUpdateToken token = new EmailUpdateToken();

    token.setUser(user);
    token.setOldEmail(user.getEmail());
    token.setNewEmail(newEmail);
    token.setToken(UUID.randomUUID().toString());
    token.setExpiresAt(LocalDateTime.now().plusMinutes(15));

    emailUpdateTokenRepository.save(token);

    mailService.sendUpdateEmailMail(
        user.getEmail(),
        token.getToken());
  }
}