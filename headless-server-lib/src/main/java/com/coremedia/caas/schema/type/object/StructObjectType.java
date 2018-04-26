package com.coremedia.caas.schema.type.object;

import com.coremedia.caas.schema.InvalidTypeDefinition;
import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.TypeDefinition;
import com.coremedia.caas.schema.datafetcher.common.KeyedDataFetcher;
import com.coremedia.caas.schema.field.common.StructFieldBuilder;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;

public class StructObjectType implements TypeDefinition {

  public static final String TYPE_NAME = "Struct";


  private StructFieldBuilder structFieldBuilder;


  public StructObjectType() {
    // field builder uses
    this.structFieldBuilder = new StructFieldBuilder(null, new KeyedDataFetcher());
  }


  @Override
  public String getName() {
    return TYPE_NAME;
  }


  @Override
  public GraphQLOutputType build(SchemaService schemaService) throws InvalidTypeDefinition {
    GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
    builder.name(getName());
    // add all common fields for struct properties
    for (GraphQLFieldDefinition fieldDefinition : structFieldBuilder.getFields()) {
      builder.field(fieldDefinition);
    }
    return builder.build();
  }
}
