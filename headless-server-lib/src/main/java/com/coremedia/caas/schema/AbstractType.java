package com.coremedia.caas.schema;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractType implements TypeDefinition {

  private String name;
  private List<FieldBuilder> fields = ImmutableList.of();
  private Map<String, String> options = ImmutableMap.of();


  public boolean hasOption(String name) {
    return Boolean.parseBoolean(options.get(name));
  }


  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<FieldBuilder> getFields() {
    return fields;
  }

  public void setFields(List<FieldBuilder> fields) {
    this.fields = fields;
  }

  public Map<String, String> getOptions() {
    return options;
  }

  public void setOptions(Map<String, String> options) {
    this.options = options;
  }


  public abstract Set<InterfaceType> getAllInterfaces(SchemaService schemaService);

  public abstract List<FieldBuilder> getFields(SchemaService schemaService) throws InvalidTypeDefinition;
}
