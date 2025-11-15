package com.example.mediajournal.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

class JwtTokenServiceTest {

  private final JwtTokenService service = new JwtTokenService();

  @Test
  void parseTokenReturnsEmptyWhenBlank() {
    assertThat(service.parseToken("  ")).isEqualTo(Optional.empty());
    assertThat(service.parseToken(null)).isEqualTo(Optional.empty());
  }

  @Test
  void parseTokenReturnsAuthenticationWithRoleUser() {
    Authentication authentication = service.parseToken("demo-token").orElseThrow();

    assertThat(authentication.getName()).isEqualTo("system-user");
    assertThat(authentication.getCredentials()).isEqualTo("demo-token");
    assertThat(authentication.getAuthorities())
        .singleElement()
        .extracting("authority")
        .isEqualTo("ROLE_USER");
  }
}
