package com.coremedia.caas.generator.config;

import java.util.List;
import java.util.Map;

public interface TypeDefinition {

  String getName();

  List<String> getInterfaces();

  List<FieldDefinition> getFields() throws InvalidTypeDefinition;

  Map<String, String> getOptions();


  void validate() throws InvalidTypeDefinition;
}
