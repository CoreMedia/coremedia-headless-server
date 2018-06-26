package com.coremedia.caas.server.service;

import com.coremedia.caas.server.service.expression.spel.SpelExpressionEvaluator;
import com.coremedia.caas.server.service.request.DefaultRequestContext;
import com.coremedia.caas.service.ServiceRegistry;
import com.coremedia.caas.service.expression.ExpressionEvaluator;
import com.coremedia.caas.service.request.RequestContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

  @Bean("modelMapper")
  @ConditionalOnMissingBean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
            .setFieldMatchingEnabled(true)
            .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
    return modelMapper;
  }


  @Bean("requestContext")
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
  public RequestContext createRequestContext() {
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
