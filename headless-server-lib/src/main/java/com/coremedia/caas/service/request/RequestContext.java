package com.coremedia.caas.service.request;

import java.util.function.UnaryOperator;
import javax.validation.constraints.NotNull;

public interface RequestContext {

  <E> E getProperty(@NotNull String propertyName, @NotNull Class<E> targetClass);

  void setProperty(@NotNull String propertyName, @NotNull Object value);

  <T> void testAndSetProperty(@NotNull String propertyName, @NotNull UnaryOperator<T> operator, @NotNull Class<T> targetClass);
}
