package com.coremedia.caas.schema;

public interface FieldDefinition {

  boolean isNonNull();

  String getName();

  String getSourceName();

  String getTypeName();
}
