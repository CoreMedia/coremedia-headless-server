package com.coremedia.caas.service.request;

public interface RequestContext {

  <E> E getProperty(String propertyName, Class<E> targetClass);

  void setProperty(String propertyName, Object value);
}
