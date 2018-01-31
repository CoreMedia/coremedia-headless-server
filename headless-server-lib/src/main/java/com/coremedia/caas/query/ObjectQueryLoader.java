package com.coremedia.caas.query;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentType;
import com.google.common.collect.ImmutableSet;
import graphql.schema.GraphQLSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static graphql.schema.GraphQLSchema.newSchema;

class ObjectQueryLoader implements QuerySchemaLoader {

  private String typeName;
  private SchemaService schema;

  // cache for built query schemas
  private Map<ContentType, GraphQLSchema> querySchemas = new ConcurrentHashMap<>();


  ObjectQueryLoader(String typeName, SchemaService schema) {
    this.typeName = typeName;
    this.schema = schema;
  }


  @Override
  public GraphQLSchema load(Object target) {
    if (!schema.isInstanceOf(target, typeName)) {
      throw new RuntimeException("Invalid Type: " + target);
    }
    Content content = (Content) target;
    // return most specific object type
    return querySchemas.computeIfAbsent(content.getType(), e -> newSchema().query(schema.getObjectType(e))
            .build(ImmutableSet.copyOf(schema.getTypes())));
  }
}
