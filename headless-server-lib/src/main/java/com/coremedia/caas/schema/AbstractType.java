package com.coremedia.caas.schema;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Set;

public abstract class AbstractType implements TypeDefinition {

  private String name;
  private List<FieldBuilder> fields = ImmutableList.of();


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


  public abstract Set<InterfaceType> getAllInterfaces(TypeDefinitionRegistry registry);

  public abstract List<FieldBuilder> getFields(TypeDefinitionRegistry registry) throws InvalidTypeDefinition;
}
