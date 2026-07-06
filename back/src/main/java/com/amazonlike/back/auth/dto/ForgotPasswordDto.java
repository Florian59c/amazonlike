package com.amazonlike.back.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ForgotPasswordDto {

  @Email(message = "Format d'email invalide")
  @NotBlank(message = "L'email est obligatoire")
  private String email;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}