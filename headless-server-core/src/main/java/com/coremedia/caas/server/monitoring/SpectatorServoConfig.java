package com.coremedia.caas.server.monitoring;

import com.netflix.spectator.api.Clock;
import com.netflix.spectator.api.Registry;
import com.netflix.spectator.servo.ServoRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("spectator-servo")
public class SpectatorServoConfig {

  @Bean
  public Registry createRegistry() {
    return new ServoRegistry(Clock.SYSTEM);
  }
}
