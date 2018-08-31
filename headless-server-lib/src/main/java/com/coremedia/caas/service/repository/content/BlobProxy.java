package com.coremedia.caas.service.repository.content;

import javax.activation.MimeType;

public interface BlobProxy {

  MimeType getContentType();

  int getSize();

  String getETag();
}
