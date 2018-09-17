package com.coremedia.caas.schema.datafetcher.content.util;

import com.coremedia.caas.service.repository.content.StructProxy;

import java.util.Map;

public class MapStruct implements StructProxy {

  public static final String NAME = "MapStruct";


  private Map<String, ?> delegate;


  public MapStruct(Map<String, ?> delegate) {
    this.delegate = delegate;
  }


  @Override
  public Object get(String propertyName) {
    return delegate.get(propertyName);
  }

  @Override
  public Map<String, ?> getProperties() {
    return delegate;
  }
}
