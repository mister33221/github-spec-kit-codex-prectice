package com.example.mediajournal.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservabilityConfig {

  @Bean
  public MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer(
      @Value("${spring.application.name:media-journal}") String applicationName) {
    return registry -> registry.config().commonTags("application", applicationName);
  }

  @Bean
  public JvmThreadMetrics jvmThreadMetrics() {
    return new JvmThreadMetrics();
  }

  @Bean
  public ClassLoaderMetrics classLoaderMetrics() {
    return new ClassLoaderMetrics();
  }

  @Bean
  public JvmMemoryMetrics jvmMemoryMetrics() {
    return new JvmMemoryMetrics();
  }

  @Bean
  public ProcessorMetrics processorMetrics() {
    return new ProcessorMetrics();
  }
}
