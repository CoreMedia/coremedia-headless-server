package com.coremedia.caas.schema.directive;

import com.coremedia.caas.execution.ExecutionContext;

import graphql.schema.DataFetchingEnvironment;

import java.util.Map;

public abstract class AbstractDirectiveEvaluator implements DirectiveEvaluator {

  private Map<String, Object> arguments;


  protected AbstractDirectiveEvaluator(Map<String, Object> arguments) {
    this.arguments = arguments;
  }


  protected <E> E getArgument(String name, Class<E> resultType) {
    return resultType.cast(arguments.get(name));
  }


  protected ExecutionContext getContext(DataFetchingEnvironment environment) {
    return environment.getContext();
  }
}
