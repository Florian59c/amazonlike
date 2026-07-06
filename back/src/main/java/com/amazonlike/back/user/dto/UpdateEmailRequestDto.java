package com.amazonlike.back.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateEmailRequestDto {

  @NotBlank(message = "L'email est obligatoire")
  @Email(message = "Format d'email invalide")
  private String newEmail;

  public String getNewEmail() {
    return newEmail;
  }

  public void setNewEmail(String newEmail) {
    this.newEmail = newEmail;
  }
}