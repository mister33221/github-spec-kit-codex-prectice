package com.example.mediajournal.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class SecurityPropertiesTest {

  @Test
  void settersOverrideDefaults() {
    SecurityProperties properties = new SecurityProperties();
    properties.setAllowedOrigins(List.of("https://media.local", "https://app.example"));
    properties.setAuthHeader("X-Auth");
    properties.setTokenPrefix("Token ");

    assertThat(properties.getAllowedOrigins())
        .containsExactly("https://media.local", "https://app.example");
    assertThat(properties.getAuthHeader()).isEqualTo("X-Auth");
    assertThat(properties.getTokenPrefix()).isEqualTo("Token ");
  }
}
