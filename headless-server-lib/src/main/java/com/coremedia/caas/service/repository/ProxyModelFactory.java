package com.coremedia.caas.service.repository;

public interface ProxyModelFactory {

  boolean appliesTo(RootContext rootContext, String modelName, Object source);

  <T> T createModel(RootContext rootContext, String modelName, Object source, Object... arguments);
}
