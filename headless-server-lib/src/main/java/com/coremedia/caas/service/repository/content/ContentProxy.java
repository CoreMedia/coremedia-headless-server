package com.coremedia.caas.service.repository.content;

import java.time.ZonedDateTime;

public interface ContentProxy extends StructProxy {

  boolean isSubtypeOf(String typeName);


  String getId();

  String getName();

  String getType();


  ZonedDateTime getCreationDate();

  ZonedDateTime getModificationDate();


  BlobProxy getBlob(String propertyName);

  MarkupProxy getMarkup(String propertyName);
}
