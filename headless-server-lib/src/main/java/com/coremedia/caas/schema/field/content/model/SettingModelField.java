package com.coremedia.caas.schema.field.content.model;

import com.coremedia.caas.schema.FieldBuilder;
import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.content.model.DirectSettingModelDataFetcher;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static com.coremedia.caas.service.repository.ModelFactory.SETTINGS_MODEL;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class SettingModelField implements FieldBuilder {

  private boolean nonNull;

  private String name;
  private String sourceName;
  private String typeName;

  private Object defaultValue;


  public boolean isNonNull() {
    return nonNull;
  }

  public void setNonNull(boolean nonNull) {
    this.nonNull = nonNull;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSourceName() {
    return sourceName == null ? name : sourceName;
  }

  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public Object getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(Object defaultValue) {
    this.defaultValue = defaultValue;
  }


  @Override
  public Collection<GraphQLFieldDefinition> build() {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcher(new DirectSettingModelDataFetcher(getSourceName(), SETTINGS_MODEL, defaultValue))
            .build());
  }
}
