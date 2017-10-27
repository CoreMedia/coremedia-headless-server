package com.coremedia.caas.generator.config;

import java.util.List;

public interface TypeDefinition {

  String getName();

  TypeDefinition getParent();

  List<FieldDefinition> getFieldDefinitions() throws InvalidTypeDefinition;

  List<InterfaceTypeDefinition> getInterfaceDefinitions();


  void validate() throws InvalidTypeDefinition;
}
