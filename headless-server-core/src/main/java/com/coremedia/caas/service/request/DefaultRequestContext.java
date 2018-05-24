package com.coremedia.caas.service.request;

import com.coremedia.caas.services.request.RequestContext;

import java.util.HashMap;
import java.util.Map;

public class DefaultRequestContext implements RequestContext {

  private Map<String, Object> properties = new HashMap<>();


  @Override
  public <E> E getProperty(String propertyName, Class<E> targetClass) {
    Object value = properties.get(propertyName);
    if (value != null && targetClass.isInstance(value)) {
      return targetClass.cast(value);
    }
    return null;
  }

  @Override
  public void setProperty(String propertyName, Object value) {
    properties.put(propertyName, value);
  }
}
