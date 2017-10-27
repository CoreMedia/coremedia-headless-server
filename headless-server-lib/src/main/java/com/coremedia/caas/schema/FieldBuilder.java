package com.coremedia.caas.schema;

import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

public interface FieldBuilder {

  String getName();

  Collection<GraphQLFieldDefinition> build();
}
