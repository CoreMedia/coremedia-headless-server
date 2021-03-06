package com.coremedia.caas.generator.config;

import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TypeCustomization {

  private String name;
  private Map<String, String> options;
  private List<String> excludedProperties = Collections.emptyList();
  private List<String> customInterfaces = Collections.emptyList();
  private Map<String, FieldDefinition> customFieldDefinitions = Collections.emptyMap();


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, String> getOptions() {
    return options;
  }

  public void setOptions(Map<String, String> options) {
    this.options = options;
  }

  public List<String> getExcludedProperties() {
    return excludedProperties;
  }

  public void setExcludedProperties(List<String> excludedProperties) {
    this.excludedProperties = excludedProperties;
  }

  public List<String> getCustomInterfaces() {
    return customInterfaces;
  }

  public void setCustomInterfaces(List<String> customInterfaces) {
    this.customInterfaces = customInterfaces;
  }

  public List<FieldDefinition> getCustomFields() {
    return ImmutableList.copyOf(customFieldDefinitions.values());
  }

  public void setCustomFields(List<FieldDefinition> customFieldDefinitions) {
    this.customFieldDefinitions = customFieldDefinitions.stream().collect(Collectors.toMap(FieldDefinition::getName, Function.identity()));
  }


  public boolean hasCustomField(String name) {
    return customFieldDefinitions.containsKey(name);
  }

  public FieldDefinition getCustomField(String name) {
    return customFieldDefinitions.get(name);
  }
}
