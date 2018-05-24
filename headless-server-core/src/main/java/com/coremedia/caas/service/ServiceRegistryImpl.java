package com.coremedia.caas.service;

import com.coremedia.caas.services.ServiceRegistry;
import com.coremedia.caas.services.expression.ExpressionEvaluator;

import org.springframework.core.convert.ConversionService;

public class ServiceRegistryImpl implements ServiceRegistry {

  private ConversionService conversionService;
  private ExpressionEvaluator expressionEvaluator;


  public ServiceRegistryImpl(ConversionService conversionService, ExpressionEvaluator expressionEvaluator) {
    this.conversionService = conversionService;
    this.expressionEvaluator = expressionEvaluator;
  }


  @Override
  public ConversionService getConversionService() {
    return conversionService;
  }

  @Override
  public ExpressionEvaluator getExpressionEvaluator() {
    return expressionEvaluator;
  }
}
