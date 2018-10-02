package com.coremedia.caas.service.repository.content;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public interface StructProxy extends ProxyObject {

  Object get(String propertyName);


  Boolean getBoolean(String propertyName);

  ZonedDateTime getDate(String propertyName);

  Integer getInteger(String propertyName);

  ContentProxy getLink(String propertyName);

  List<ContentProxy> getLinks(String propertyName);

  String getString(String propertyName);

  StructProxy getStruct(String propertyName);


  Map<String, ?> getProperties();
}
