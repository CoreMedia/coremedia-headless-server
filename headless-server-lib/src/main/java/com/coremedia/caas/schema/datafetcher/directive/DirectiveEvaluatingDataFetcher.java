package com.coremedia.caas.schema.datafetcher.directive;

import com.coremedia.caas.schema.DirectiveDefinition;
import com.coremedia.caas.schema.datafetcher.common.AbstractDataFetcher;
import com.coremedia.caas.schema.directive.DirectiveEvaluator;
import com.coremedia.caas.schema.directive.EvaluationResult;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import javax.validation.constraints.NotNull;

public class DirectiveEvaluatingDataFetcher extends AbstractDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(DirectiveEvaluatingDataFetcher.class);


  private DataFetcher delegate;

  /* directives defined in the field definition, they are always applied and take precedence to directives defined in the query */
  private List<DirectiveDefinition> implicitDirectives;


  public DirectiveEvaluatingDataFetcher(@NotNull DataFetcher delegate, List<DirectiveDefinition> implicitDirectives) {
    this.delegate = delegate;
    this.implicitDirectives = implicitDirectives;
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    boolean fetchField = true;
    Object resolvedField = null;
    // get defined directive evaluators
    DirectiveInstrumentation instrumentation = new DirectiveInstrumentation(implicitDirectives, environment);
    // run pre fetch directives on source object
    for (DirectiveEvaluator evaluator : instrumentation.getPreFetchEvaluators()) {
      EvaluationResult evaluationResult = evaluator.evaluate(environment.getSource(), environment);
      switch (evaluationResult.getAction()) {
        case RETURN: {
          return evaluationResult.getValue();
        }
        case REPLACE: {
          fetchField = false;
          resolvedField = evaluationResult.getValue();
          break;
        }
      }
    }
    // fetch field value if not replaced by prefetch directive
    if (fetchField) {
      resolvedField = delegate.get(environment);
    }
    // run post fetch directives on field value
    for (DirectiveEvaluator evaluator : instrumentation.getPostFetchEvaluators()) {
      EvaluationResult evaluationResult = evaluator.evaluate(resolvedField, environment);
      switch (evaluationResult.getAction()) {
        case RETURN: {
          return evaluationResult.getValue();
        }
        case REPLACE: {
          resolvedField = evaluationResult.getValue();
          break;
        }
      }
    }
    return resolvedField;
  }
}
