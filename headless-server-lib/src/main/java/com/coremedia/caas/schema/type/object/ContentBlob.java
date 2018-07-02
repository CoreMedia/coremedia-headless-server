package com.coremedia.caas.schema.type.object;

import com.coremedia.caas.schema.InvalidTypeDefinition;
import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.TypeDefinition;
import com.coremedia.caas.schema.datafetcher.StaticDataFetcherFactory;

import graphql.Scalars;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;
import graphql.schema.PropertyDataFetcher;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class ContentBlob implements TypeDefinition {

  public static final String TYPENAME = "ContentBlob";

  public static final String PROPERTY_CONTENTTYPE = "contentType";
  public static final String PROPERTY_SIZE = "size";


  @Override
  public String getName() {
    return TYPENAME;
  }

  @Override
  public GraphQLType build(SchemaService schemaService) throws InvalidTypeDefinition {
    GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
    builder.name(getName());
    builder.field(newFieldDefinition()
            .name(PROPERTY_CONTENTTYPE)
            .type(Scalars.GraphQLString)
            .dataFetcherFactory(new StaticDataFetcherFactory(new PropertyDataFetcher(PROPERTY_CONTENTTYPE)))
            .build());
    builder.field(newFieldDefinition()
            .name(PROPERTY_SIZE)
            .type(Scalars.GraphQLInt)
            .dataFetcherFactory(new StaticDataFetcherFactory(new PropertyDataFetcher(PROPERTY_SIZE)))
            .build());
    return builder.build();
  }
}
