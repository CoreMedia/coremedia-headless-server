package com.coremedia.caas.schema;

import graphql.schema.GraphQLOutputType;

public interface TypeDefinition {

  String getName();

  GraphQLOutputType build(TypeDefinitionRegistry registry) throws InvalidTypeDefinition;
}
