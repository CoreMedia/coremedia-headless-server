package com.coremedia.caas.server.service.request;

import com.coremedia.caas.server.interceptor.ResponseHeaderInitializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestConfig {

  @Bean("responseHeaderInitializer")
  public ResponseHeaderInitializer responseHeaderInitializer() {
    return new ResponseHeaderInitializer();
  }
}
