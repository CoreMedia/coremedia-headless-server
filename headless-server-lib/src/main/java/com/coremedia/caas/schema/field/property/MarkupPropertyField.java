package com.coremedia.caas.schema.field.property;

import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.converter.ConvertingDataFetcher;
import com.coremedia.caas.schema.datafetcher.property.MarkupPropertyDataFetcher;
import com.coremedia.caas.schema.field.common.AbstractField;
import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class MarkupPropertyField extends AbstractField {

  @Override
  public Collection<GraphQLFieldDefinition> build() {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcher(new ConvertingDataFetcher(getTypeName(), new MarkupPropertyDataFetcher(getSourceName())))
            .build());
  }
}
