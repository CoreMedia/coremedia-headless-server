package com.coremedia.caas.schema.field.common;

import com.coremedia.caas.schema.DirectiveDefinition;
import com.coremedia.caas.schema.FieldBuilder;
import com.coremedia.caas.schema.FieldDefinition;
import com.coremedia.caas.schema.datafetcher.StaticDataFetcherFactory;
import com.coremedia.caas.schema.datafetcher.converter.ConvertingDataFetcher;
import com.coremedia.caas.schema.datafetcher.directive.DirectiveEvaluatingDataFetcher;

import com.google.common.base.MoreObjects;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactory;

import java.util.List;

public abstract class AbstractField implements FieldDefinition, FieldBuilder {

  private boolean convertible;
  private boolean withDirectives;

  private boolean nonNull;

  private String name;
  private String sourceName;
  private List<String> fallbackSourceNames;
  private List<DirectiveDefinition> directives;
  private String typeName;
  private Object defaultValue;


  protected AbstractField(boolean convertible, boolean withDirectives) {
    this.convertible = convertible;
    this.withDirectives = withDirectives;
  }


  protected boolean isConvertible() {
    return convertible;
  }

  protected boolean isWithDirectives() {
    return withDirectives;
  }


  protected DataFetcherFactory decorate(DataFetcher dataFetcher) {
    if (isWithDirectives()) {
      dataFetcher = new DirectiveEvaluatingDataFetcher(dataFetcher, getDirectives());
    }
    if (isConvertible()) {
      dataFetcher = new ConvertingDataFetcher(getTypeName(), dataFetcher);
    }
    return new StaticDataFetcherFactory(dataFetcher);
  }


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
  public List<DirectiveDefinition> getDirectives() {
    return directives;
  }

  public void setDirectives(List<DirectiveDefinition> directives) {
    this.directives = directives;
  }

  @Override
  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  @Override
  public Object getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(Object defaultValue) {
    this.defaultValue = defaultValue;
  }


  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("name", name)
            .add("sourceName", sourceName)
            .add("fallbackSourceNames", fallbackSourceNames)
            .add("directives", directives)
            .add("typeName", typeName)
            .add("defaultValue", defaultValue)
            .add("nonNull", nonNull)
            .add("convertible", convertible)
            .add("withDirectives", withDirectives)
            .toString();
  }
}
