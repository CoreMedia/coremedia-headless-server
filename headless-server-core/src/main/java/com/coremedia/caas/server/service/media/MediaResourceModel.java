package com.coremedia.caas.server.service.media;

public interface MediaResourceModel {

  String getType();

  MediaResource getMediaResource(String ratio, int minWidth, int minHeight);
}
