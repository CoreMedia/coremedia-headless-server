package com.coremedia.caas.schema.datafetcher.directive;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.schema.DirectiveDefinition;
import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.directive.CustomDirective;
import com.coremedia.caas.schema.directive.DirectiveEvaluator;
import com.coremedia.caas.schema.directive.Phase;

import com.google.common.collect.ImmutableList;
import graphql.GraphQLException;
import graphql.language.Argument;
import graphql.language.BooleanValue;
import graphql.language.Directive;
import graphql.language.Field;
import graphql.language.FloatValue;
import graphql.language.IntValue;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.language.VariableReference;
import graphql.schema.DataFetchingEnvironment;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

public class DirectiveInstrumentation {

  private List<DirectiveDefinition> implicitDirectives;
  private DataFetchingEnvironment environment;

  private List<DirectiveEvaluator> evaluators;


  DirectiveInstrumentation(List<DirectiveDefinition> implicitDirectives, DataFetchingEnvironment environment) {
    this.implicitDirectives = implicitDirectives;
    this.environment = environment;
    // fetch evaluators from runtime
    this.evaluators = fetchRuntimeEvaluators();
  }


  @NotNull
  List<DirectiveEvaluator> getPreFetchEvaluators() {
    return evaluators.stream().filter(e -> e.getPhase() == Phase.PREFETCH).collect(Collectors.toList());
  }

  @NotNull
  List<DirectiveEvaluator> getPostFetchEvaluators() {
    return evaluators.stream().filter(e -> e.getPhase() == Phase.POSTFETCH).collect(Collectors.toList());
  }


  private List<DirectiveEvaluator> fetchRuntimeEvaluators() {
    ImmutableList.Builder<DirectiveDefinition> definitionBuilder = ImmutableList.builder();
    // implicit directive are always executed first
    if (implicitDirectives != null) {
      definitionBuilder.addAll(implicitDirectives);
    }
    // append query field directives
    Field field = environment.getField();
    if (field != null) {
      List<Directive> directives = field.getDirectives();
      if (directives != null && !directives.isEmpty()) {
        definitionBuilder.addAll(directives.stream().map(RuntimeDirectiveDefinition::new).collect(Collectors.toList()));
      }
    }
    List<DirectiveDefinition> definitions = definitionBuilder.build();
    // create runtime evaluators
    if (!definitions.isEmpty()) {
      SchemaService schemaService = ((ExecutionContext) environment.getContext()).getProcessingDefinition().getSchemaService();
      // undefined directives are ignored
      ImmutableList.Builder<DirectiveEvaluator> builder = ImmutableList.builder();
      for (DirectiveDefinition definition : definitions) {
        CustomDirective customDirective = schemaService.getCustomDirective(definition.getName());
        if (customDirective != null) {
          builder.add(customDirective.createEvaluator(definition.getArguments()));
        }
      }
      return builder.build();
    }
    return Collections.emptyList();
  }


  private class RuntimeDirectiveDefinition implements DirectiveDefinition {

    private Directive directive;

    private RuntimeDirectiveDefinition(Directive directive) {
      this.directive = directive;
    }

    @Override
    public String getName() {
      return directive.getName();
    }

    @Override
    public Map<String, Object> getArguments() {
      Map<String, Object> arguments = new HashMap<>(5);
      for (Argument arg : directive.getArguments()) {
        Value value = arg.getValue();
        if (value instanceof BooleanValue) {
          arguments.put(arg.getName(), ((BooleanValue) value).isValue());
        }
        else if (value instanceof FloatValue) {
          arguments.put(arg.getName(), ((FloatValue) value).getValue());
        }
        else if (value instanceof IntValue) {
          arguments.put(arg.getName(), ((IntValue) value).getValue());
        }
        else if (value instanceof StringValue) {
          arguments.put(arg.getName(), ((StringValue) value).getValue());
        }
        else if (value instanceof VariableReference) {
          String variableName = ((VariableReference) value).getName();
          Object variableValue = environment.getExecutionContext().getVariables().get(variableName);
          arguments.put(arg.getName(), variableValue);
        }
        else {
          throw new GraphQLException("Runtime argument not resolved");
        }
      }
      return arguments;
    }
  }
}
