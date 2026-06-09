package com.amazonlike.back.exception;

import com.amazonlike.back.common.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDto> handleValidationErrors(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    ErrorResponseDto response = new ErrorResponseDto(
        "Validation échouée",
        errors);

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponseDto> handleResponseStatus(ResponseStatusException ex) {
    return ResponseEntity
        .status(ex.getStatusCode())
        .body(new ErrorResponseDto(ex.getReason(), null));
  }
}