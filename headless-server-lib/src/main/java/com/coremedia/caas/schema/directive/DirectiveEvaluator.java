package com.coremedia.caas.schema.directive;

import graphql.schema.DataFetchingEnvironment;

public interface DirectiveEvaluator {

  Phase getPhase();

  EvaluationResult evaluate(Object source, DataFetchingEnvironment environment);
}
