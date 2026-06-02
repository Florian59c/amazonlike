package com.amazonlike.back.auth.service;

import com.amazonlike.back.auth.dto.RegisterDto;
import com.amazonlike.back.user.entity.User;
import com.amazonlike.back.user.repository.UserRepository;
import com.amazonlike.back.user.role.Role;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthService(UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void register(RegisterDto request) {
    if (!request.getPassword().equals(request.getConfirmPassword())) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Les mots de passe ne correspondent pas");
    }

    if (userRepository.existsByEmail(request.getEmail())) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Cette adresse email est déjà utilisée");
    }

    User user = new User();
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setRole(Role.USER);
    user.setEnabled(true);
    user.setLocked(false);
    user.setTokenVersion(0);

    userRepository.save(user);
  }
}