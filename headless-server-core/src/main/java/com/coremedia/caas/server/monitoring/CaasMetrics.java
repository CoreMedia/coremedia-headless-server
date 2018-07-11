package com.coremedia.caas.server.monitoring;

import io.micrometer.core.instrument.MeterRegistry;

import java.util.function.Supplier;
import javax.validation.constraints.NotNull;

public class CaasMetrics {

  private MeterRegistry registry;


  public CaasMetrics(MeterRegistry registry) {
    this.registry = registry;
  }


  public <E> E timer(@NotNull Supplier<E> supplier, @NotNull String timerName, @NotNull String... tags) {
    return registry.timer(timerName, tags).record(supplier);
  }
}
