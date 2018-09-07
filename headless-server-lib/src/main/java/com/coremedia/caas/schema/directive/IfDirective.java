package com.coremedia.caas.schema.directive;

import com.coremedia.caas.schema.datafetcher.DataFetcherException;
import com.coremedia.caas.service.expression.ExpressionEvaluator;

import com.google.common.base.Strings;
import graphql.Scalars;
import graphql.introspection.Introspection;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLDirective;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static graphql.schema.GraphQLDirective.newDirective;

public class IfDirective implements CustomDirective {

  private static final Logger LOG = LoggerFactory.getLogger(IfDirective.class);


  private static final String NAME = "if";

  private static final String ARGUMENT_TEST = "test";
  private static final String ARGUMENT_ELSE = "else";


  @Override
  public String getName() {
    return NAME;
  }


  @Override
  public GraphQLDirective createDirective() {
    return newDirective()
            .name(NAME)
            .description("A directive evaluating a boolean expression on the current root object and returning a default value if the expression is evaluated to false")
            .argument(GraphQLArgument.newArgument().name(ARGUMENT_TEST).type(Scalars.GraphQLString).description("The boolean expression"))
            .argument(GraphQLArgument.newArgument().name(ARGUMENT_ELSE).type(Scalars.GraphQLString).description("The default return value"))
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
      try {
        ExpressionEvaluator evaluator = getContext(environment).getServiceRegistry().getExpressionEvaluator();
        // if expression evaluates to true do nothing, that is fetch the field if no other directive says otherwise
        // else the field value is replaced with null or the result of an optional 'else' expression
        if (evaluator.evaluate(getArgument(ARGUMENT_TEST, String.class), source, Boolean.class)) {
          return EvaluationResult.NOOP;
        }
        else {
          String expression = getArgument(ARGUMENT_ELSE, String.class);
          if (!Strings.isNullOrEmpty(expression)) {
            return new EvaluationResult(Action.RETURN, evaluator.evaluate(expression, source, Object.class));
          }
          else {
            return EvaluationResult.RETNULL;
          }
        }
      } catch (Exception e) {
        LOG.warn("Directive evaluation failed: {}", e.getMessage());
        throw new DataFetcherException("If directive evaluation failed", e);
      }
    }
  }
}
