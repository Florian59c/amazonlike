package com.amazonlike.back.user.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.amazonlike.back.user.entity.User;
import com.amazonlike.back.user.repository.UserRepository;

@Service
public class UserCleanupService {

  private final UserRepository userRepository;

  public UserCleanupService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Scheduled(cron = "0 0 3 * * *")
  public void purgeDeletedUsers() {

    LocalDateTime limit = LocalDateTime.now().minusDays(30);

    List<User> users = userRepository.findAllByDeletedAtBefore(limit)
        .stream()
        .filter(u -> u.getDeletedAt() != null)
        .toList();

    userRepository.deleteAll(users);
  }
}