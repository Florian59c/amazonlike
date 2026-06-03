package com.amazonlike.back.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.amazonlike.back.user.dto.UpdateProfileDto;
import com.amazonlike.back.user.entity.User;
import com.amazonlike.back.user.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
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

  public User updateProfile(User user, UpdateProfileDto request) {

    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());

    return userRepository.save(user);
  }
}