package com.coremedia.caas.schema.field.common;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.common.MetaPropertyDataFetcher;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class MetaPropertyField extends AbstractField {

  public MetaPropertyField() {
    super(true, false);
  }


  @Override
  public Collection<GraphQLFieldDefinition> build(SchemaService schemaService) {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcherFactory(decorate(new MetaPropertyDataFetcher(getSourceName())))
            .build());
  }
}
