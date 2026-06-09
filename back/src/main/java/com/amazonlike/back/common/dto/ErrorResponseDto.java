package com.amazonlike.back.common.dto;

import java.util.Map;

public class ErrorResponseDto {

  private String message;
  private Map<String, String> errors;

  public ErrorResponseDto(String message, Map<String, String> errors) {
    this.message = message;
    this.errors = errors;
  }

  public String getMessage() {
    return message;
  }

  public Map<String, String> getErrors() {
    return errors;
  }
}