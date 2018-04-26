package com.coremedia.caas.generator.config;

import java.util.Map;

public class DirectiveDefinition {

  private String name;
  private Map<String, String> arguments;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, String> getArguments() {
    return arguments;
  }

  public void setArguments(Map<String, String> arguments) {
    this.arguments = arguments;
  }
}
