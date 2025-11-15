package com.example.mediajournal.config;

import java.time.Instant;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
    var body =
        Map.<String, Object>of(
            "timestamp", Instant.now(),
            "status", HttpStatus.BAD_REQUEST.value(),
            "error", "Validation failed",
            "message", ex.getMessage());
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
    log.error("Unhandled exception", ex);
    var body =
        Map.<String, Object>of(
            "timestamp", Instant.now(),
            "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "error", "Server error",
            "message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}
