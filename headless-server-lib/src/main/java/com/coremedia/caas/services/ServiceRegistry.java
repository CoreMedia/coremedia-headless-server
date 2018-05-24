package com.coremedia.caas.services;

import com.coremedia.caas.services.expression.ExpressionEvaluator;

import org.springframework.core.convert.ConversionService;

public interface ServiceRegistry {

  ConversionService getConversionService();

  ExpressionEvaluator getExpressionEvaluator();
}
