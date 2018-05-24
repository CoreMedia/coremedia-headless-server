package com.coremedia.caas.service.expression.spel;

import com.coremedia.caas.services.expression.ExpressionEvaluator;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpelExpressionEvaluator implements ExpressionEvaluator {

  private static ExpressionParser parser = new SpelExpressionParser();

  private static Map<String, Expression> expressionCache = new ConcurrentHashMap<>();


  private StandardEvaluationContext context;
  private List<PropertyAccessor> propertyAccessors;


  public SpelExpressionEvaluator(List<PropertyAccessor> propertyAccessors) {
    this.propertyAccessors = propertyAccessors;
  }


  @Override
  public void init(Map<String, Object> variables) {
    context = new StandardEvaluationContext();
    context.setVariables(variables);
    context.setPropertyAccessors(propertyAccessors);
  }


  @Override
  public <E> E evaluate(String expression, Object rootObject, Class<E> resultType) {
    return expressionCache.computeIfAbsent(expression, e -> parser.parseExpression(e)).getValue(context, rootObject, resultType);
  }
}
