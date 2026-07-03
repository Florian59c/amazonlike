package com.amazonlike.back.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordDto {

  @NotBlank
  private String token;

  @NotBlank(message = "Le mot de passe est obligatoire")
  @Size(min = 8, message = "Le mot de passe doit faire au moins 8 caractères")
  // regex à ajouter plus tard pour vérifier la complexité du mot de passe
  private String password;

  @NotBlank(message = "La confirmation du mot de passe est obligatoire")
  private String confirmPassword;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}