package com.coremedia.caas.monitoring;

import com.netflix.spectator.api.Id;
import com.netflix.spectator.api.Registry;
import com.netflix.spectator.api.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class Metrics {

  private Registry registry;

  private Map<String, Id> registeredIds = new ConcurrentHashMap<>();


  @Autowired
  public Metrics(Registry registry) {
    this.registry = registry;
  }


  private Timer getTimer(String name, String... tags) {
    StringBuilder builder = new StringBuilder(name);
    for (String tag : tags) {
      builder.append('#').append(tag);
    }
    String key = builder.toString();
    // fetch existing or create new ID
    Id id = registeredIds.computeIfAbsent(key, e -> registry.createId(name, tags));
    // return timer
    return registry.timer(id);
  }


  public <E> E timer(Supplier<E> supplier, String timerName, String... tags) {
    long start = registry.clock().monotonicTime();
    try {
      return supplier.get();
    } finally {
      long end = registry.clock().monotonicTime();
      getTimer(timerName, tags).record(end - start, TimeUnit.NANOSECONDS);
    }
  }
}
