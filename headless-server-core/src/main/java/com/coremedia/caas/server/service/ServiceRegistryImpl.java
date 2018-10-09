package com.coremedia.caas.server.service;

import com.coremedia.caas.service.ServiceRegistry;
import com.coremedia.caas.service.expression.ExpressionEvaluator;

import org.springframework.cache.CacheManager;
import org.springframework.core.convert.ConversionService;

public class ServiceRegistryImpl implements ServiceRegistry {

  private CacheManager cacheManager;
  private ConversionService conversionService;
  private ExpressionEvaluator expressionEvaluator;


  public ServiceRegistryImpl(CacheManager cacheManager, ConversionService conversionService, ExpressionEvaluator expressionEvaluator) {
    this.cacheManager = cacheManager;
    this.conversionService = conversionService;
    this.expressionEvaluator = expressionEvaluator;
  }


  @Override
  public CacheManager getCacheManager() {
    return cacheManager;
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
