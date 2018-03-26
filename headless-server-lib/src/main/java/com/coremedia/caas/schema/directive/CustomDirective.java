package com.coremedia.caas.schema.directive;

import graphql.schema.GraphQLDirective;

import java.util.Map;

public interface CustomDirective {

  String getName();

  GraphQLDirective createDirective();

  DirectiveEvaluator createEvaluator(Map<String, Object> arguments);
}
