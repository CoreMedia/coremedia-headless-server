package com.coremedia.caas.schema;

import graphql.schema.GraphQLType;

public interface TypeDefinition {

  String getName();

  GraphQLType build(SchemaService schemaService) throws InvalidTypeDefinition;
}
