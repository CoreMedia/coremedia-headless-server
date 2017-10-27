package com.coremedia.caas.schema.field.property;

import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.property.StructPropertyDataFetcher;
import com.coremedia.caas.schema.field.common.AbstractField;
import com.coremedia.caas.schema.type.StructObjectType;
import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class StructPropertyField extends AbstractField {

  @Override
  public Collection<GraphQLFieldDefinition> build() {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(StructObjectType.TYPE_NAME, isNonNull()))
            .dataFetcher(new StructPropertyDataFetcher(getSourceName()))
            .build());
  }
}
