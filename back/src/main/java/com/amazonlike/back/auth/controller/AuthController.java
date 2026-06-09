package com.amazonlike.back.auth.controller;

import com.amazonlike.back.auth.dto.LoginDto;
import com.amazonlike.back.auth.dto.RegisterDto;
import com.amazonlike.back.auth.service.AuthService;
import com.amazonlike.back.user.entity.User;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@Valid @RequestBody RegisterDto request) {
    authService.register(request);
    return ResponseEntity.ok("Votre compte a été créé avec succès");
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(
      @Valid @RequestBody LoginDto request,
      HttpServletResponse response) {
    authService.login(request, response);
    return ResponseEntity.ok("Connection réussi");
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(
      @AuthenticationPrincipal User user,
      HttpServletResponse response) {
    authService.logout(user, response);
    return ResponseEntity.ok("Déconnection réussi");
  }
}