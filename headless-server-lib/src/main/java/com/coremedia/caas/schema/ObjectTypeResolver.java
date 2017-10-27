package com.coremedia.caas.schema;

import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;

public class ObjectTypeResolver implements TypeResolver {

  private SchemaService schemaService;


  public ObjectTypeResolver(SchemaService schemaService) {
    this.schemaService = schemaService;
  }


  @Override
  public GraphQLObjectType getType(TypeResolutionEnvironment environment) {
    return schemaService.getObjectType(environment.getObject());
  }
}
