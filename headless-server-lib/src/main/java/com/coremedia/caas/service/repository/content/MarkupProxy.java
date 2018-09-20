package com.coremedia.caas.service.repository.content;

public interface MarkupProxy extends ProxyObject {

  boolean isEmpty();

  long weight();

  String asXml();
}
