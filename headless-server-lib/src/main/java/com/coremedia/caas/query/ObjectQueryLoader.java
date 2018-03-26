package com.coremedia.caas.query;

import com.coremedia.caas.schema.SchemaService;

import graphql.GraphQLException;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static graphql.schema.GraphQLSchema.newSchema;

class ObjectQueryLoader implements QuerySchemaLoader {

  private String typeName;
  private SchemaService schema;

  // cache for built query schemas
  private Map<String, GraphQLSchema> querySchemas = new ConcurrentHashMap<>();


  ObjectQueryLoader(String typeName, SchemaService schema) {
    this.typeName = typeName;
    this.schema = schema;
  }


  @Override
  public GraphQLSchema load(Object target) {
    if (!schema.isInstanceOf(target, typeName)) {
      throw new GraphQLException("Invalid root type: " + target);
    }
    GraphQLObjectType type = schema.getObjectType(target);
    if (type == null) {
      throw new GraphQLException("No object type for root: " + target);
    }
    return querySchemas.computeIfAbsent(type.getName(), __ -> newSchema().query(type).build(schema.getTypes(), schema.getDirectives()));
  }
}
