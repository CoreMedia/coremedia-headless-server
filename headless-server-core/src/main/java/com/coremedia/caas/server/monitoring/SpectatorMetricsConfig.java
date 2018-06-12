package com.coremedia.caas.server.monitoring;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.netflix.spectator.api.Clock;
import com.netflix.spectator.api.Registry;
import com.netflix.spectator.metrics3.MetricsRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("spectator-metrics")
public class SpectatorMetricsConfig {

  @Bean
  public MetricRegistry createMetricsRegistry() {
    return new MetricRegistry();
  }

  @Bean(initMethod = "start", destroyMethod = "close")
  @ConditionalOnProperty("spectator.metrics.exposeMBeans")
  public JmxReporter createJmxReporter(MetricRegistry metricRegistry) {
    return JmxReporter.forRegistry(metricRegistry).build();
  }

  @Bean
  public Registry createRegistry(MetricRegistry metricRegistry) {
    return new MetricsRegistry(Clock.SYSTEM, metricRegistry);
  }
}
