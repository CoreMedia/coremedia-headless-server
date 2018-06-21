package com.coremedia.caas.schema.datafetcher.directive;

import com.coremedia.caas.schema.DirectiveDefinition;
import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.datafetcher.common.AbstractDataFetcher;
import com.coremedia.caas.schema.directive.CustomDirective;
import com.coremedia.caas.schema.directive.DirectiveEvaluator;
import com.coremedia.caas.schema.directive.EvaluationResult;
import com.coremedia.caas.schema.directive.Phase;
import com.coremedia.caas.schema.util.RuntimeUtil;

import com.google.common.collect.ImmutableList;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
    List<DirectiveEvaluator> evaluators = getDirectiveEvaluators(environment);
    // run pre fetch directives on source object
    for (DirectiveEvaluator evaluator : evaluators) {
      if (evaluator.getPhase() == Phase.PREFETCH) {
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
    }
    // fetch field value if not replaced by prefetch directive
    if (fetchField) {
      resolvedField = delegate.get(environment);
    }
    // run post fetch directives on field value
    for (DirectiveEvaluator evaluator : evaluators) {
      if (evaluator.getPhase() == Phase.POSTFETCH) {
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
    }
    return resolvedField;
  }


  @NotNull
  private List<DirectiveEvaluator> getDirectiveEvaluators(DataFetchingEnvironment environment) {
    SchemaService schemaService = getContext(environment).getProcessingDefinition().getSchemaService();
    // undefined directives are ignored
    ImmutableList.Builder<DirectiveEvaluator> builder = ImmutableList.builder();
    for (DirectiveDefinition definition : getDirectiveDefinitions(environment)) {
      CustomDirective customDirective = schemaService.getCustomDirective(definition.getName());
      if (customDirective != null) {
        builder.add(customDirective.createEvaluator(definition.getArguments()));
      }
      else {
        LOG.warn("Undefined directive @{} found", definition.getName());
      }
    }
    return builder.build();
  }

  @NotNull
  private List<DirectiveDefinition> getDirectiveDefinitions(DataFetchingEnvironment environment) {
    List<DirectiveDefinition> definitions = new ArrayList<>();
    if (implicitDirectives != null) {
      definitions.addAll(implicitDirectives);
    }
    definitions.addAll(RuntimeUtil.getFieldDirectiveDefinitions(environment));
    return definitions;
  }
}
