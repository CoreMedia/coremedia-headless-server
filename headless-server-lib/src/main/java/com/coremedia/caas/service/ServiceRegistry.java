package com.coremedia.caas.service;

import com.coremedia.caas.service.expression.ExpressionEvaluator;

import org.springframework.cache.CacheManager;
import org.springframework.core.convert.ConversionService;

public interface ServiceRegistry {

  CacheManager getCacheManager();

  ConversionService getConversionService();

  ExpressionEvaluator getExpressionEvaluator();
}
