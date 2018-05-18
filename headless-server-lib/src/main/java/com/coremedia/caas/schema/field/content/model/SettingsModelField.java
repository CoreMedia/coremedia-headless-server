package com.coremedia.caas.schema.field.content.model;

import com.coremedia.caas.schema.FieldBuilder;
import com.coremedia.caas.schema.datafetcher.content.model.DirectSettingModelDataFetcher;
import com.coremedia.caas.schema.field.common.StructFieldBuilder;

import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static com.coremedia.caas.services.repository.ModelFactory.SETTINGS_MODEL;

public class SettingsModelField implements FieldBuilder {

  private String name;


  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public Collection<GraphQLFieldDefinition> build() {
    StructFieldBuilder builder = new StructFieldBuilder("Setting", new DirectSettingModelDataFetcher(SETTINGS_MODEL));
    return builder.getFields();
  }
}
