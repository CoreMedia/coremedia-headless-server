package com.coremedia.caas.schema.directive;

import com.coremedia.caas.service.expression.ExpressionEvaluator;

import com.google.common.base.Strings;
import graphql.Scalars;
import graphql.introspection.Introspection;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLDirective;

import java.util.Map;

import static graphql.schema.GraphQLDirective.newDirective;

public class IfDirective implements CustomDirective {

  private static final String NAME = "if";


  @Override
  public String getName() {
    return NAME;
  }


  @Override
  public GraphQLDirective createDirective() {
    return newDirective()
            .name(NAME)
            .description("A directive evaluating a boolean expression on the current root object and returning a default value if the expression is evaluated to false")
            .argument(GraphQLArgument.newArgument().name("test").type(Scalars.GraphQLString).description("The boolean expression"))
            .argument(GraphQLArgument.newArgument().name("else").type(Scalars.GraphQLString).description("The default return value"))
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
      return Phase.PREFETCH;
    }

    @Override
    public EvaluationResult evaluate(final Object source, final DataFetchingEnvironment environment) {
      ExpressionEvaluator evaluator = getContext(environment).getServiceRegistry().getExpressionEvaluator();
      // if expression evaluates to true do nothing, that is fetch the field if no other directive says otherwise
      // else the field value is replaced with null or the result of an optional 'else' expression
      if (evaluator.evaluate(getArgument("test", String.class), source, Boolean.class)) {
        return EvaluationResult.NOOP;
      }
      else {
        return new EvaluationResult() {
          @Override
          public Action getAction() {
            return Action.RETURN;
          }

          @Override
          public Object getValue() {
            String elseExpression = getArgument("else", String.class);
            if (!Strings.isNullOrEmpty(elseExpression)) {
              return evaluator.evaluate(elseExpression, source, Object.class);
            }
            return null;
          }
        };
      }
    }
  }
}
