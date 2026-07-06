package com.amazonlike.back.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazonlike.back.user.entity.EmailUpdateToken;
import com.amazonlike.back.user.entity.User;

public interface EmailUpdateTokenRepository
    extends JpaRepository<EmailUpdateToken, UUID> {

  Optional<EmailUpdateToken> findByToken(String token);

  void deleteAllByUser(User user);
}