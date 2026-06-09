package com.amazonlike.back.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterDto {

  @NotBlank(message = "L'email est obligatoire")
  @Email(message = "Format d'email invalide")
  @Size(max = 100, message = "Email trop long")
  private String email;

  @NotBlank(message = "Le mot de passe est obligatoire")
  @Size(min = 8, message = "Le mot de passe doit faire au moins 8 caractères")
  // regex à ajouter plus tard pour vérifier la complexité du mot de passe
  private String password;

  @NotBlank(message = "La confirmation du mot de passe est obligatoire")
  private String confirmPassword;

  @NotBlank(message = "Le prénom est obligatoire")
  @Size(max = 50, message = "Prénom trop long")
  private String firstName;

  @NotBlank(message = "Le nom est obligatoire")
  @Size(max = 50, message = "Nom trop long")
  private String lastName;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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