package com.coremedia.caas.service.expression;

public interface FieldExpressionEvaluator<T> {

  Object fetch(T expression, Object source);

  <E> E fetch(T expression, Object source, Class<E> targetClass);
}
