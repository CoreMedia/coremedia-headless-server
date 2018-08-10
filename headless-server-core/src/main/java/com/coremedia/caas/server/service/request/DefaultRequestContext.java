package com.coremedia.caas.server.service.request;

import com.coremedia.caas.service.request.RequestContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;
import javax.validation.constraints.NotNull;

public class DefaultRequestContext implements RequestContext {

  private final Map<String, Object> properties = new ConcurrentHashMap<>();


  @Override
  public <T> T getProperty(@NotNull String propertyName, @NotNull Class<T> targetClass) {
    Object value = properties.get(propertyName);
    if (value != null && targetClass.isInstance(value)) {
      return targetClass.cast(value);
    }
    return null;
  }

  @Override
  public void setProperty(@NotNull String propertyName, @NotNull Object value) {
    properties.put(propertyName, value);
  }

  @Override
  public <T> void testAndSetProperty(@NotNull String propertyName, @NotNull UnaryOperator<T> operator, @NotNull Class<T> targetClass) {
    synchronized (properties) {
      setProperty(propertyName, operator.apply(getProperty(propertyName, targetClass)));
    }
  }
}
