package com.amazonlike.back.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.amazonlike.back.user.role.Role;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String LastName;

  @Column(nullable = false)
  private String FirstName;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  private boolean enabled = true;

  private boolean locked = false;

  private int tokenVersion;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}