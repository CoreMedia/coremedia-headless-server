package com.coremedia.caas.service.request;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.function.UnaryOperator;

public interface RequestContext {

  boolean isPreview();

  ZonedDateTime getRequestTime();
  void setRequestTime(ZonedDateTime time);

  <E> E getProperty(@NotNull String propertyName, @NotNull Class<E> targetClass);

  void setProperty(@NotNull String propertyName, @NotNull Object value);

  <T> void testAndSetProperty(@NotNull String propertyName, @NotNull UnaryOperator<T> operator, @NotNull Class<T> targetClass);
}
