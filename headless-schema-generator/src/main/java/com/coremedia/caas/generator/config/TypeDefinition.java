package com.coremedia.caas.generator.config;

import java.util.List;
import java.util.Map;

public interface TypeDefinition {

  String getName();

  TypeDefinition getParent();

  Map<String, String> getOptions();

  List<FieldDefinition> getFieldDefinitions() throws InvalidTypeDefinition;

  List<InterfaceTypeDefinition> getInterfaceDefinitions();


  void validate() throws InvalidTypeDefinition;
}
