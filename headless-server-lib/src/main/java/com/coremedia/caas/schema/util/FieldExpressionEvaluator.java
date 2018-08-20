package com.coremedia.caas.schema.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

@Component
public class FieldExpressionEvaluator {

  private static final Logger LOG = LoggerFactory.getLogger(FieldExpressionEvaluator.class);


  private static final SpelParserConfiguration PARSER_CONFIGURATION = new SpelParserConfiguration(SpelCompilerMode.OFF, FieldExpressionEvaluator.class.getClassLoader());

  private static final SpelExpressionParser EXPRESSION_PARSER = new SpelExpressionParser(PARSER_CONFIGURATION);

  private static EvaluationContext EVALUATION_CONTEXT;


  public static Expression compile(String pathExpression) {
    try {
      return EXPRESSION_PARSER.parseExpression(pathExpression);
    } catch (Exception e) {
      LOG.error("Expression \"{}\" has errors: {}", pathExpression, e.getMessage());
      throw e;
    }
  }

  public static Object fetch(Expression expression, Object source) {
    return expression.getValue(EVALUATION_CONTEXT, source);
  }

  public static <E> E fetch(Expression expression, Object source, Class<E> targetClass) {
    return expression.getValue(EVALUATION_CONTEXT, source, targetClass);
  }


  public FieldExpressionEvaluator(@Qualifier("schemaEvaluationContext") EvaluationContext evaluationContext) {
    EVALUATION_CONTEXT = evaluationContext;
  }
}
