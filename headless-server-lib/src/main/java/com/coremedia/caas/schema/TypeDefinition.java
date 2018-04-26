package com.coremedia.caas.schema;

import graphql.schema.GraphQLOutputType;

public interface TypeDefinition {

  String getName();

  GraphQLOutputType build(SchemaService schemaService) throws InvalidTypeDefinition;
}
