package com.coremedia.caas.schema.field.content.model;

import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.content.model.DirectSettingModelDataFetcher;
import com.coremedia.caas.schema.field.common.AbstractField;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static com.coremedia.caas.service.repository.ModelFactory.SETTINGS_MODEL;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class SettingModelField extends AbstractField {

  public SettingModelField() {
    super(true, true);
  }


  @Override
  public Collection<GraphQLFieldDefinition> build() {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcherFactory(decorate(new DirectSettingModelDataFetcher(getSourceName(), SETTINGS_MODEL, getDefaultValue())))
            .build());
  }
}
