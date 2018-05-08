package com.coremedia.caas.schema.field.content.property;

import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.property.ContentPropertyDataFetcher;
import com.coremedia.caas.schema.field.common.AbstractField;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class ContentPropertyField extends AbstractField {

  public ContentPropertyField() {
    super(true, true);
  }


  @Override
  public Collection<GraphQLFieldDefinition> build() {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcherFactory(decorate(new ContentPropertyDataFetcher(getSourceName(), getFallbackSourceNames())))
            .build());
  }
}
