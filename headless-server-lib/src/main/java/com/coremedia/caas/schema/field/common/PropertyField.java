package com.coremedia.caas.schema.field.common;

import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.common.PropertyDataFetcher;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class PropertyField extends AbstractField {

  public PropertyField() {
    super(true, true);
  }


  @Override
  public Collection<GraphQLFieldDefinition> build() {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcherFactory(decorate(new PropertyDataFetcher(getSourceName())))
            .build());
  }
}
