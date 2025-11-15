package com.example.mediajournal.security;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

  /** Allowed origins for CORS configuration. */
  private List<String> allowedOrigins = List.of("http://localhost:4200");

  /** Header name that carries JWT tokens. */
  private String authHeader = "Authorization";

  /** Prefix used in Authorization header, e.g., Bearer. */
  private String tokenPrefix = "Bearer ";

  public List<String> getAllowedOrigins() {
    return allowedOrigins;
  }

  public void setAllowedOrigins(List<String> allowedOrigins) {
    this.allowedOrigins = allowedOrigins;
  }

  public String getAuthHeader() {
    return authHeader;
  }

  public void setAuthHeader(String authHeader) {
    this.authHeader = authHeader;
  }

  public String getTokenPrefix() {
    return tokenPrefix;
  }

  public void setTokenPrefix(String tokenPrefix) {
    this.tokenPrefix = tokenPrefix;
  }
}
