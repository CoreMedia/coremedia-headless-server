package com.coremedia.caas.service.expression;

public class FieldExpression<T> {

  private T expression;
  private FieldExpressionEvaluator<T> evaluator;


  public FieldExpression(T expression, FieldExpressionEvaluator<T> evaluator) {
    this.expression = expression;
    this.evaluator = evaluator;
  }


  public Object fetch(Object source) {
    return evaluator.fetch(expression, source);
  }

  public <E> E fetch(Object source, Class<E> targetClass) {
    return evaluator.fetch(expression, source, targetClass);
  }
}
