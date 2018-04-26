package com.coremedia.caas.schema.directive;

import graphql.Scalars;
import graphql.introspection.Introspection;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLDirective;

import java.util.Map;

import static graphql.schema.GraphQLDirective.newDirective;

public class FilterDirective implements CustomDirective {

  private static final String NAME = "filter";


  @Override
  public String getName() {
    return NAME;
  }


  @Override
  public GraphQLDirective createDirective() {
    return newDirective()
            .name(NAME)
            .description("A directive evaluating an expression on a field's value and replacing it with the expressions return value")
            .argument(GraphQLArgument.newArgument().name("test").type(Scalars.GraphQLString).description("The filter expression"))
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
      return new EvaluationResult() {
        @Override
        public Action getAction() {
          return Action.REPLACE;
        }

        @Override
        public Object getValue() {
          return getContext(environment).getServiceRegistry().getExpressionEvaluator().evaluate(getArgument("test", String.class), source, Object.class);
        }
      };
    }
  }
}
