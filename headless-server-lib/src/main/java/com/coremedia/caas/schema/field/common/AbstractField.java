package com.coremedia.caas.schema.field.common;

import com.coremedia.caas.schema.FieldBuilder;
import com.coremedia.caas.schema.FieldDefinition;
import com.google.common.base.MoreObjects;

import java.util.List;

public abstract class AbstractField implements FieldDefinition, FieldBuilder {

  private boolean nonNull;

  private String name;
  private String sourceName;
  private List<String> fallbackSourceNames;
  private String typeName;


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
  public String getSourceName() {
    return sourceName == null ? name : sourceName;
  }

  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }

  @Override
  public List<String> getFallbackSourceNames() {
    return fallbackSourceNames;
  }

  public void setFallbackSourceNames(List<String> fallbackSourceNames) {
    this.fallbackSourceNames = fallbackSourceNames;
  }

  @Override
  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }


  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("name", name)
            .add("sourceName", sourceName)
            .add("fallbackSourceNames", fallbackSourceNames)
            .add("typeName", typeName)
            .add("nonNull", nonNull)
            .toString();
  }
}
