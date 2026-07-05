package com.amazonlike.back.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "email_update_tokens")
public class EmailUpdateToken {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false)
  private String oldEmail;

  @Column(nullable = false)
  private String newEmail;

  @Column(nullable = false)
  private LocalDateTime expiresAt;

  @OneToOne
  @JoinColumn(nullable = false)
  private User user;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getOldEmail() {
    return oldEmail;
  }

  public void setOldEmail(String oldEmail) {
    this.oldEmail = oldEmail;
  }

  public String getNewEmail() {
    return newEmail;
  }

  public void setNewEmail(String newEmail) {
    this.newEmail = newEmail;
  }

  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(LocalDateTime expiresAt) {
    this.expiresAt = expiresAt;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}