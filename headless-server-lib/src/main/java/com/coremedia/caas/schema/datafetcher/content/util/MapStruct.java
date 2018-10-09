package com.coremedia.caas.schema.datafetcher.content.util;

import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.caas.service.repository.content.StructProxy;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MapStruct implements StructProxy {

  public static final String NAME = "MapStruct";


  private Map<String, ?> delegate;


  public MapStruct(Map<String, ?> delegate) {
    this.delegate = delegate;
  }


  private <E> E get(String propertyName, Class<E> targetType) {
    Object value = delegate.get(propertyName);
    if (value != null && targetType.isAssignableFrom(value.getClass())) {
      return targetType.cast(value);
    }
    return null;
  }


  @Override
  public Object get(String propertyName) {
    return delegate.get(propertyName);
  }


  @Override
  public Boolean getBoolean(String propertyName) {
    return get(propertyName, Boolean.class);
  }

  @Override
  public ZonedDateTime getDate(String propertyName) {
    return get(propertyName, ZonedDateTime.class);
  }

  @Override
  public Integer getInteger(String propertyName) {
    return get(propertyName, Integer.class);
  }

  @Override
  @SuppressWarnings("unchecked")
  public ContentProxy getLink(String propertyName) {
    Object value = delegate.get(propertyName);
    if (value instanceof List) {
      return ((List<ContentProxy>) value).get(0);
    }
    return (ContentProxy) value;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ContentProxy> getLinks(String propertyName) {
    Object value = delegate.get(propertyName);
    if (value instanceof List) {
      return (List<ContentProxy>) value;
    }
    return Collections.singletonList((ContentProxy) value);
  }

  @Override
  public String getString(String propertyName) {
    return get(propertyName, String.class);
  }

  @Override
  public StructProxy getStruct(String propertyName) {
    return get(propertyName, StructProxy.class);
  }


  @Override
  public Map<String, ?> getProperties() {
    return delegate;
  }
}
