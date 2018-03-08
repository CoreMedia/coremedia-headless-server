package com.coremedia.caas.query;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.query.RootDataFetcher;
import com.google.common.collect.ImmutableSet;
import graphql.GraphQLException;
import graphql.schema.GraphQLSchema;

import java.util.List;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;
import static graphql.schema.GraphQLSchema.newSchema;

class ListQueryLoader implements QuerySchemaLoader {

  // fixed schema instance
  private GraphQLSchema querySchema;


  ListQueryLoader(String queryName, String typeName, SchemaService schema) {
    querySchema = newSchema().query(newObject().name(queryName + "QueryType")
                                            .field(newFieldDefinition().name("items")
                                                           .type(Types.getType(typeName, true))
                                                           .dataFetcher(new RootDataFetcher()))
                                            .build())
            .build(ImmutableSet.copyOf(schema.getTypes()));
  }


  @Override
  public GraphQLSchema load(Object target) {
    if (!(target instanceof List)) {
      throw new GraphQLException("Invalid root type: " + target);
    }
    return querySchema;
  }
}
