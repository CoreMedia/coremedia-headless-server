package com.coremedia.caas.service.repository;

public interface ProxyModelFactory {

  boolean appliesTo(String modelName, String propertyPath, Object source, RootContext rootContext);

  <T> T createModel(String modelName, String propertyPath, Object source, RootContext rootContext);
}
