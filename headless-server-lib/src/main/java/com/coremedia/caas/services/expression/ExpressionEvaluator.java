package com.coremedia.caas.services.expression;

public interface ExpressionEvaluator {

  <E> E evaluate(String expression, Object rootObject, Class<E> resultType);
}
