package com.coremedia.caas.schema.field.content.property;

import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.content.property.RichtextPropertyDataFetcher;
import com.coremedia.caas.schema.field.common.AbstractField;

import com.google.common.collect.ImmutableList;
import graphql.Scalars;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class RichtextPropertyField extends AbstractField {

  public RichtextPropertyField() {
    super(false, true);
  }


  @Override
  public Collection<GraphQLFieldDefinition> build() {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .argument(new GraphQLArgument("view", Scalars.GraphQLString))
            .dataFetcherFactory(decorate(new RichtextPropertyDataFetcher(getSourceName(), getFallbackSourceNames())))
            .build());
  }
}
