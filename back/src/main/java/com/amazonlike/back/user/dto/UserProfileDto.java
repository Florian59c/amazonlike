package com.amazonlike.back.user.dto;

public class UserProfileDto {

  private String firstName;
  private String lastName;

  public UserProfileDto(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
}