package com.coremedia.caas.schema.type.field;

import com.coremedia.caas.schema.FieldBuilder;
import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.StaticDataFetcherFactory;
import com.coremedia.caas.schema.datafetcher.common.PropertyDataFetcher;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class ThisField implements FieldBuilder {

  public static final String NAME = "this";


  private String targetType;


  public ThisField(String targetType) {
    this.targetType = targetType;
  }


  @Override
  public String getName() {
    return NAME;
  }


  @Override
  public Collection<GraphQLFieldDefinition> build(SchemaService schemaService) {
    return ImmutableList.of(newFieldDefinition()
            .name(NAME)
            .type(Types.getType(targetType, true))
            .dataFetcherFactory(new StaticDataFetcherFactory(new PropertyDataFetcher(NAME)))
            .build());
  }
}
