package com.coremedia.caas.services.repository.content;

public interface ContentProxy {

  boolean isSubtypeOf(String typeName);

  String getId();

  String getName();

  String getType();

  Object get(String propertyName);
}
