package com.coremedia.caas.services.repository;

public interface ProxyFactory {

  RootContext getRootContext();


  Object makeProxy(Object source);
}
