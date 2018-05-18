package com.coremedia.caas.service.security;

import com.coremedia.caas.interceptor.RequestDateInitializer;
import com.coremedia.caas.services.request.RequestContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AccessControlConfig {

  @Bean
  public ValidityDateValidator validityDateValidator() {
    return new ValidityDateValidator();
  }


  @Bean("requestDateInitializer")
  @Profile("!preview")
  public RequestDateInitializer liveRequestDateInitializer(RequestContext requestContext) {
    return new RequestDateInitializer(false, requestContext);
  }

  @Bean("requestDateInitializer")
  @Profile("preview")
  public RequestDateInitializer previewRequestDateInitializer(RequestContext requestContext) {
    return new RequestDateInitializer(true, requestContext);
  }
}
