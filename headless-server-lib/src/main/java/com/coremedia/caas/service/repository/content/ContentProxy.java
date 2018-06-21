package com.coremedia.caas.service.repository.content;

import com.coremedia.cap.common.Blob;

import java.util.List;

public interface ContentProxy {

  boolean isSubtypeOf(String typeName);


  String getId();

  String getName();

  String getType();


  Object get(String propertyName);


  Blob getBlob(String propertyName);

  Boolean getBoolean(String propertyName);

  Integer getInteger(String propertyName);

  ContentProxy getLink(String propertyName);

  List<ContentProxy> getLinks(String propertyName);

  String getString(String propertyName);
}
