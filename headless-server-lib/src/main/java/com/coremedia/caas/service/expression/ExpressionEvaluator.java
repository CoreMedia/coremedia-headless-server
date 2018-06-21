package com.coremedia.caas.service.expression;

import java.util.Map;

public interface ExpressionEvaluator {

  void init(Map<String, Object> variables);

  <E> E evaluate(String expression, Object rootObject, Class<E> resultType);
}
