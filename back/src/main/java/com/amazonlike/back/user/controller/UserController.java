package com.amazonlike.back.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonlike.back.user.dto.UpdateProfileDto;
import com.amazonlike.back.user.dto.UserProfileDto;
import com.amazonlike.back.user.entity.User;
import com.amazonlike.back.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/getAllUsers")
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable UUID id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  @GetMapping("/getProfile")
  public ResponseEntity<UserProfileDto> getMyProfile(
      @AuthenticationPrincipal User user) {

    return ResponseEntity.ok(userService.getCurrentUserProfile(user));
  }

  @PatchMapping("/updateProfile")
  public ResponseEntity<String> updateProfile(
      @AuthenticationPrincipal User user,
      @Valid @RequestBody UpdateProfileDto request) {

    userService.updateProfile(user, request);

    return ResponseEntity.ok("Le profil a bien été modifié");
  }

  @DeleteMapping("/deleteAccount")
  public ResponseEntity<String> deleteAccount(
      @AuthenticationPrincipal User user) {

    userService.deleteCurrentUser(user);

    return ResponseEntity.ok("Compte désactivé");
  }
}