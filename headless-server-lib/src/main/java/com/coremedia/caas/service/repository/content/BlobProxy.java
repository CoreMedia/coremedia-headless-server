package com.coremedia.caas.service.repository.content;

import javax.activation.MimeType;

public interface BlobProxy {

  boolean isEmpty();

  int getSize();

  MimeType getContentType();

  String getETag();
}
