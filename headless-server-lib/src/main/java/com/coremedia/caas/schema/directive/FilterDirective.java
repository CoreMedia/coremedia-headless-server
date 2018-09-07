package com.coremedia.caas.schema.directive;

import com.coremedia.caas.schema.datafetcher.DataFetcherException;

import graphql.Scalars;
import graphql.introspection.Introspection;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLDirective;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static graphql.schema.GraphQLDirective.newDirective;

public class FilterDirective implements CustomDirective {

  private static final Logger LOG = LoggerFactory.getLogger(FilterDirective.class);


  private static final String NAME = "filter";

  private static final String ARGUMENT_TEST = "test";


  @Override
  public String getName() {
    return NAME;
  }


  @Override
  public GraphQLDirective createDirective() {
    return newDirective()
            .name(NAME)
            .description("A directive evaluating an expression on a field's value and replacing it with the expressions return value")
            .argument(GraphQLArgument.newArgument().name(ARGUMENT_TEST).type(Scalars.GraphQLString).description("The filter expression"))
            .validLocations(Introspection.DirectiveLocation.FIELD)
            .build();
  }


  @Override
  public DirectiveEvaluator createEvaluator(Map<String, Object> arguments) {
    return new Evaluator(arguments);
  }


  private static class Evaluator extends AbstractDirectiveEvaluator {

    private Evaluator(Map<String, Object> arguments) {
      super(arguments);
    }

    @Override
    public Phase getPhase() {
      return Phase.POSTFETCH;
    }

    @Override
    public EvaluationResult evaluate(final Object source, final DataFetchingEnvironment environment) {
      try {
        Object value = getContext(environment).getServiceRegistry().getExpressionEvaluator().evaluate(getArgument(ARGUMENT_TEST, String.class), source, Object.class);
        return new EvaluationResult(Action.REPLACE, value);
      } catch (Exception e) {
        LOG.warn("Directive evaluation failed: {}", e.getMessage());
        throw new DataFetcherException("Filter directive evaluation failed", e);
      }
    }
  }
}
