package com.coremedia.caas.server.service.request;

import com.coremedia.caas.server.CaasServiceConfig;
import com.coremedia.caas.server.interceptor.ResponseHeaderInitializer;
import com.coremedia.caas.service.request.RequestContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class RequestConfig {

  @Bean("responseHeaderInitializer")
  public ResponseHeaderInitializer responseHeaderInitializer() {
    return new ResponseHeaderInitializer();
  }


  @Bean("requestContext")
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
  public RequestContext createPreviewRequestContext(CaasServiceConfig serviceConfig) {
    return new DefaultRequestContext(serviceConfig.isPreview());
  }
}
