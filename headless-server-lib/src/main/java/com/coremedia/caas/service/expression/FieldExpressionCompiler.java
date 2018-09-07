package com.coremedia.caas.service.expression;

public interface FieldExpressionCompiler {

  FieldExpression<?> compile(String pathExpression);
}
