package com.amazonlike.back.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateProfileDto {

  @NotBlank(message = "Le prénom est obligatoire")
  @Size(max = 50)
  private String firstName;

  @NotBlank(message = "Le nom est obligatoire")
  @Size(max = 50)
  private String lastName;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}