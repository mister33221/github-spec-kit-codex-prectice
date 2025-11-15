package com.example.mediajournal.config;

import static org.assertj.core.api.Assertions.assertThat;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

class ObservabilityConfigTest {

  private final ObservabilityConfig config = new ObservabilityConfig();

  @Test
  void meterRegistryCustomizerAddsApplicationTag() {
    var registry = new SimpleMeterRegistry();
    config.meterRegistryCustomizer("media-journal").customize(registry);

    var counter = registry.counter("test.metric");
    assertThat(counter.getId().getTag("application")).isEqualTo("media-journal");
  }

  @Test
  void exposesJvmMetricBinders() {
    assertThat(config.jvmThreadMetrics()).isNotNull();
    assertThat(config.classLoaderMetrics()).isNotNull();
    assertThat(config.jvmMemoryMetrics()).isNotNull();
    assertThat(config.processorMetrics()).isNotNull();
  }
}
