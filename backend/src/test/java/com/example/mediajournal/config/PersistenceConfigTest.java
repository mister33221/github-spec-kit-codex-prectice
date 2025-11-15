package com.example.mediajournal.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class PersistenceConfigTest {

  private final PersistenceConfig config = new PersistenceConfig();

  @Test
  void auditorAwareReturnsEmptyOptional() {
    assertThat(config.auditorAware().getCurrentAuditor()).isEqualTo(Optional.empty());
  }
}
