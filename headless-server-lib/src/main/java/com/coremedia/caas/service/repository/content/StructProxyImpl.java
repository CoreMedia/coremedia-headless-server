package com.coremedia.caas.service.repository.content;

import com.coremedia.caas.service.repository.ProxyFactory;
import com.coremedia.cap.struct.Struct;

import java.util.Map;
import java.util.Objects;

public class StructProxyImpl implements StructProxy {

  public static final Class<StructProxy> TARGET_CLASS = StructProxy.class;
  public static final Class[] TARGET_CLASSES = new Class[]{TARGET_CLASS};


  private Struct delegate;
  private ProxyFactory proxyFactory;


  public StructProxyImpl(Struct delegate, ProxyFactory proxyFactory) {
    this.delegate = delegate;
    this.proxyFactory = proxyFactory;
  }


  @Override
  public Object get(String propertyName) {
    return proxyFactory.makeProxy(delegate.get(propertyName));
  }

  @Override
  public Map<String, ?> getProperties() {
    return proxyFactory.makeProxyMap(delegate.getProperties());
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StructProxyImpl that = (StructProxyImpl) o;
    return Objects.equals(delegate, that.delegate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(delegate);
  }
}
