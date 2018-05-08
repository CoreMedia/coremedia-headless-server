package com.coremedia.caas.schema.field.content.adapter.settings;

import com.coremedia.caas.schema.FieldBuilder;
import com.coremedia.caas.schema.datafetcher.settings.DirectSettingDataFetcher;
import com.coremedia.caas.schema.field.common.StructFieldBuilder;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

public class SettingsField implements FieldBuilder {

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
    StructFieldBuilder builder = new StructFieldBuilder("Setting", new DirectSettingDataFetcher());
    return builder.getFields();
  }
}
