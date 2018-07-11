package com.coremedia.caas.server.monitoring;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaasMetricsConfig {

  @Bean
  public CaasMetrics caasMetrics(MeterRegistry meterRegistry) {
    return new CaasMetrics(meterRegistry);
  }
}
