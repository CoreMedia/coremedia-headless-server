package com.coremedia.caas.server.service.security;

import com.coremedia.caas.server.interceptor.RequestDateInitializer;
import com.coremedia.caas.service.request.RequestContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccessControlConfig {

  @Bean("validityDateValidator")
  public ValidityDateValidator validityDateValidator() {
    return new ValidityDateValidator();
  }


  @Bean("requestDateInitializer")
  public RequestDateInitializer requestDateInitializer(RequestContext requestContext) {
    return new RequestDateInitializer(requestContext);
  }
}
