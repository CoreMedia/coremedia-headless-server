package com.coremedia.caas.service.repository.content;

import com.coremedia.caas.service.repository.ProxyFactory;
import com.coremedia.cap.common.CapStructHelper;
import com.coremedia.cap.struct.Struct;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.coremedia.caas.service.repository.content.util.ContentUtil.getZonedDateTime;

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
  public Boolean getBoolean(String propertyName) {
    return CapStructHelper.getBoolean(delegate, propertyName);
  }

  @Override
  public ZonedDateTime getDate(String propertyName) {
    return getZonedDateTime(delegate, propertyName);
  }

  @Override
  public Integer getInteger(String propertyName) {
    return CapStructHelper.getInteger(delegate, propertyName);
  }

  @Override
  public ContentProxy getLink(String propertyName) {
    // return the first valid item, so ensure the complete list is fetched
    List<ContentProxy> contentProxies = getLinks(propertyName);
    if (!contentProxies.isEmpty()) {
      return contentProxies.get(0);
    }
    return null;
  }

  @Override
  public List<ContentProxy> getLinks(String propertyName) {
    return proxyFactory.makeContentProxyList(CapStructHelper.getLinks(delegate, propertyName));
  }

  @Override
  public String getString(String propertyName) {
    return CapStructHelper.getString(delegate, propertyName);
  }

  @Override
  public StructProxy getStruct(String propertyName) {
    return proxyFactory.makeStructProxy(CapStructHelper.getStruct(delegate, propertyName));
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
