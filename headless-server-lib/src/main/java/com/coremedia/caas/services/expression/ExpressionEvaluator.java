package com.coremedia.caas.services.expression;

import java.util.Map;

public interface ExpressionEvaluator {

  void setVariables(Map<String, Object> variables);

  <E> E evaluate(String expression, Object rootObject, Class<E> resultType);
}
