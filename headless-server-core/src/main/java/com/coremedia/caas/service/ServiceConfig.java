package com.coremedia.caas.service;

import com.coremedia.caas.service.expression.spel.SpelExpressionEvaluator;
import com.coremedia.caas.service.request.DefaultRequestContext;
import com.coremedia.caas.services.ServiceRegistry;
import com.coremedia.caas.services.expression.ExpressionEvaluator;
import com.coremedia.caas.services.request.RequestContext;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.convert.ConversionService;
import org.springframework.expression.PropertyAccessor;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Configuration
public class ServiceConfig {

  @Bean("requestContext")
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
  public RequestContext createReqCont() {
    return new DefaultRequestContext();
  }


  @Bean("spelEvaluator")
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
  public ExpressionEvaluator createSpelExpressionEvaluator(@Qualifier("spelPropertyAccessors") List<PropertyAccessor> propertyAccessors) {
    return new SpelExpressionEvaluator(propertyAccessors);
  }


  @Bean
  public ServiceRegistry createServiceRegistry(
          @Qualifier("dataFetcherConversionService") ConversionService conversionService,
          @Qualifier("spelEvaluator") ExpressionEvaluator expressionEvaluator) {
    return new ServiceRegistryImpl(conversionService, expressionEvaluator);
  }
}
