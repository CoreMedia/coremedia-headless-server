package com.coremedia.caas.generator.config;

public interface FieldDefinition {

  boolean isNonNull();

  String getName();

  String getSourceName();

  String getTargetType();

  String getTypeName();
}
