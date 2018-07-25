package com.coremedia.caas.service.repository.content;

import java.util.Calendar;
import java.util.List;

public interface ContentProxy {

  boolean isSubtypeOf(String typeName);


  String getId();

  String getName();

  String getType();


  Calendar getCreationDate();

  Calendar getModificationDate();


  Object get(String propertyName);


  BlobProxy getBlob(String propertyName);

  Boolean getBoolean(String propertyName);

  Integer getInteger(String propertyName);

  ContentProxy getLink(String propertyName);

  List<ContentProxy> getLinks(String propertyName);

  MarkupProxy getMarkup(String propertyName);

  String getString(String propertyName);
}
