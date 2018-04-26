package com.coremedia.caas.schema.directive;

import com.coremedia.caas.schema.DirectiveDefinition;

import com.google.common.base.MoreObjects;

import java.util.Map;

public class FieldDirective implements DirectiveDefinition {

  private String name;
  private Map<String, Object> arguments;


  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Map<String, Object> getArguments() {
    return arguments;
  }

  public void setArguments(Map<String, Object> arguments) {
    this.arguments = arguments;
  }


  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("name", name)
            .add("arguments", arguments)
            .toString();
  }
}
