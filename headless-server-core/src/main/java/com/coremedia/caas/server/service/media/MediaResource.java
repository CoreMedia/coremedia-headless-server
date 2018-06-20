package com.coremedia.caas.server.service.media;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

public interface MediaResource extends Resource {

  String getETag();

  MediaType getMediaType();
}
