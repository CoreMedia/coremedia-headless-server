package com.coremedia.caas.generator.config;

import java.util.Collections;
import java.util.List;

public class CustomFieldDefinition implements FieldDefinition {

  private boolean nonNull;

  private String name;
  private String targetType;
  private String sourceName;
  private List<String> fallbackSourceNames;
  private List<DirectiveDefinition> directives;
  private String dataFetcher;
  private Object defaultValue;


  @Override
  public boolean isNonNull() {
    return nonNull;
  }

  public void setNonNull(boolean nonNull) {
    this.nonNull = nonNull;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getTargetType() {
    return targetType;
  }

  public void setTargetType(String targetType) {
    this.targetType = targetType;
  }

  @Override
  public String getSourceName() {
    if (sourceName != null) {
      return sourceName;
    }
    return getName();
  }

  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }

  @Override
  public List<String> getFallbackSourceNames() {
    return fallbackSourceNames != null ? fallbackSourceNames : Collections.emptyList();
  }

  public void setFallbackSourceNames(List<String> fallbackSourceNames) {
    this.fallbackSourceNames = fallbackSourceNames;
  }

  @Override
  public List<DirectiveDefinition> getDirectives() {
    return directives;
  }

  public void setDirectives(List<DirectiveDefinition> directives) {
    this.directives = directives;
  }


  public String getDataFetcher() {
    return dataFetcher;
  }

  public void setDataFetcher(String dataFetcher) {
    this.dataFetcher = dataFetcher;
  }

  public Object getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(Object defaultValue) {
    this.defaultValue = defaultValue;
  }


  @Override
  public String getTypeName() {
    return getDataFetcher().toLowerCase();
  }
}
