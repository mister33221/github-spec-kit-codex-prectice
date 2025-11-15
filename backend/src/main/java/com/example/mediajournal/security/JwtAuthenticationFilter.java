package com.example.mediajournal.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenService tokenService;
  private final SecurityProperties securityProperties;
  private final AuditLogger auditLogger;

  public JwtAuthenticationFilter(
      JwtTokenService tokenService,
      SecurityProperties securityProperties,
      AuditLogger auditLogger) {
    this.tokenService = tokenService;
    this.securityProperties = securityProperties;
    this.auditLogger = auditLogger;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String header = request.getHeader(securityProperties.getAuthHeader());
    if (header != null && header.startsWith(securityProperties.getTokenPrefix())) {
      String token = header.substring(securityProperties.getTokenPrefix().length());
      tokenService
          .parseToken(token)
          .ifPresent(
              authentication -> {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                auditLogger.log("AUTH", authentication.getName());
              });
    }
    filterChain.doFilter(request, response);
  }
}
