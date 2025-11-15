package com.example.mediajournal.security;

import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuditLogger {

  private static final Logger log = LoggerFactory.getLogger(AuditLogger.class);

  public void log(String action, String subject) {
    log.info("[AUDIT] action={} subject={} at={}", action, subject, Instant.now());
  }
}
