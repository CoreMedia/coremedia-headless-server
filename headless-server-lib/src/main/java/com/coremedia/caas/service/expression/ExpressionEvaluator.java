package com.coremedia.caas.service.expression;

public interface ExpressionEvaluator {

  <E> E evaluate(String expression, Object rootObject, Class<E> resultType);
}
