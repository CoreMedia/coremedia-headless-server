package com.coremedia.caas.schema.field.content.model;

import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.content.model.NavigationModelDataFetcher;
import com.coremedia.caas.schema.field.common.AbstractField;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static com.coremedia.caas.service.repository.ModelFactory.NAVIGATION_MODEL;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class NavigationModelField extends AbstractField {

  public NavigationModelField() {
    super(false, true);
  }


  @Override
  public Collection<GraphQLFieldDefinition> build() {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcherFactory(decorate(new NavigationModelDataFetcher(getSourceName(), NAVIGATION_MODEL)))
            .build());
  }
}
