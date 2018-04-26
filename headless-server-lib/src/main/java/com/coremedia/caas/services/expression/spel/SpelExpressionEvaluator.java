package com.coremedia.caas.services.expression.spel;

import com.coremedia.caas.services.expression.ExpressionEvaluator;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpelExpressionEvaluator implements ExpressionEvaluator {

  private static ExpressionParser parser = new SpelExpressionParser();

  private static Map<String, Expression> expressionCache = new ConcurrentHashMap<>();


  private StandardEvaluationContext context = new StandardEvaluationContext();


  @Override
  public void setVariables(Map<String, Object> variables) {
    context.setVariables(variables);
  }


  @Override
  public <E> E evaluate(String expression, Object rootObject, Class<E> resultType) {
    return expressionCache.computeIfAbsent(expression, e -> parser.parseExpression(e)).getValue(context, rootObject, resultType);
  }
}
