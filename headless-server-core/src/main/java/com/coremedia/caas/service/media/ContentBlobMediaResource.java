package com.coremedia.caas.service.media;

import com.coremedia.cap.common.Blob;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;

class ContentBlobMediaResource implements MediaResource {

  private Blob blob;


  ContentBlobMediaResource(Blob blob) {
    this.blob = blob;
  }


  @Override
  public long getSize() {
    return blob.getSize();
  }

  @Override
  public MediaType getMediaType() {
    return MediaType.parseMediaType(blob.getContentType().toString());
  }

  @Override
  public InputStreamResource getInputStreamResource() {
    return new InputStreamResource(blob.getInputStream());
  }
}
