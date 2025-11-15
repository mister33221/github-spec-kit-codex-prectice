package com.example.mediajournal.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @Test
  void handleGenericReturnsServerErrorPayload() {
    var response = handler.handleGeneric(new IllegalStateException("boom"));

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).containsEntry("message", "boom");
  }

  @Test
  void handleValidationReturnsBadRequestPayload() throws NoSuchMethodException {
    var bindingResult = new BeanPropertyBindingResult(new Object(), "payload");
    bindingResult.reject("invalid", "payload is invalid");
    var parameter =
        new MethodParameter(
            GlobalExceptionHandlerTest.class.getDeclaredMethod("sampleMethod", String.class), 0);

    var exception = new MethodArgumentNotValidException(parameter, bindingResult);
    var response = handler.handleValidation(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody())
        .containsEntry("error", "Validation failed")
        .containsEntry("status", HttpStatus.BAD_REQUEST.value());
  }

  @SuppressWarnings("unused")
  private static void sampleMethod(String input) {}
}
