package com.coremedia.caas.query;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.query.RootDataFetcher;
import com.google.common.collect.ImmutableSet;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import java.util.Map;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;
import static graphql.schema.GraphQLSchema.newSchema;

public class QueryDefinition {

  private String query;
  private String name;
  private String viewName;
  private String typeName;

  private GraphQLSchema querySchema;


  public QueryDefinition(String queryString, Map<String, String> queryArgs, SchemaService schema) {
    init(queryString, queryArgs, schema);
  }


  private void init(String queryString, Map<String, String> queryArgs, SchemaService schema) {
    query = queryString;
    name = queryArgs.get("name");
    typeName = queryArgs.get("type");
    viewName = queryArgs.get("view");

    if (Types.isList(typeName)) {
      GraphQLObjectType objectType = newObject()
              .name(name + "QueryType")
              .field(newFieldDefinition()
                      .name("data")
                      .type(Types.getType(typeName, true))
                      .dataFetcher(new RootDataFetcher()))
              .build();
      this.querySchema = newSchema()
              .query(objectType)
              .build(ImmutableSet.copyOf(schema.getTypes()));
    } else {
      GraphQLObjectType objectType = schema.getObjectType(typeName);
      this.querySchema = newSchema()
              .query(objectType)
              .build(ImmutableSet.copyOf(schema.getTypes()));
    }
  }


  public String getName() {
    return name;
  }

  public String getTypeName() {
    return typeName;
  }

  public String getViewName() {
    return viewName;
  }

  public String getQuery() {
    return query;
  }


  public GraphQLSchema getQuerySchema() {
    return querySchema;
  }
}
