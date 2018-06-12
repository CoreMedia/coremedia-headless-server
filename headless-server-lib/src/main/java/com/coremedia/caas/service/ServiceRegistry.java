package com.coremedia.caas.service;

import com.coremedia.caas.service.expression.ExpressionEvaluator;

import org.springframework.core.convert.ConversionService;

public interface ServiceRegistry {

  ConversionService getConversionService();

  ExpressionEvaluator getExpressionEvaluator();
}
