package com.coremedia.caas.service.media;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;

public interface MediaResource {

  long getSize();

  MediaType getMediaType();

  InputStreamResource getInputStreamResource();
}
