package com.coremedia.caas.server.service.security;

import com.coremedia.caas.server.interceptor.RequestDateInitializer;
import com.coremedia.caas.service.request.RequestContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AccessControlConfig {

  @Bean("validityDateValidator")
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
