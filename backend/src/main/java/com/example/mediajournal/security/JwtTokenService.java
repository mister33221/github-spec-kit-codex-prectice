package com.example.mediajournal.security;

import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenService {

  public Optional<Authentication> parseToken(String token) {
    if (token == null || token.isBlank()) {
      return Optional.empty();
    }
    // TODO: Replace with real JWT validation once identity provider is defined.
    var auth =
        new UsernamePasswordAuthenticationToken(
            "system-user", token, java.util.List.of(new SimpleGrantedAuthority("ROLE_USER")));
    return Optional.of(auth);
  }
}
