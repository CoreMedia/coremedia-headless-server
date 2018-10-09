package com.coremedia.caas.service.repository.content;

import com.coremedia.caas.service.repository.ProxyFactory;
import com.coremedia.cap.common.Blob;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.struct.Struct;
import com.coremedia.xml.Markup;

import com.google.common.base.MoreObjects;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.coremedia.caas.service.repository.content.util.ContentUtil.getZonedDateTime;
import static com.coremedia.caas.service.repository.content.util.ContentUtil.toZonedDateTime;

public class ContentProxyImpl implements ContentProxy {

  public static final Class<ContentProxyImpl> TARGET_CLASS = ContentProxyImpl.class;
  public static final Class[] TARGET_CLASSES = new Class[]{TARGET_CLASS};


  private final Content delegate;
  private final ProxyFactory proxyFactory;


  public ContentProxyImpl(Content delegate, ProxyFactory proxyFactory) {
    this.delegate = delegate;
    this.proxyFactory = proxyFactory;
  }


  @Override
  public boolean isSubtypeOf(String typeName) {
    return delegate.getType().isSubtypeOf(typeName);
  }


  @Override
  public String getId() {
    return delegate.getId();
  }

  @Override
  public String getName() {
    return delegate.getName();
  }

  @Override
  public String getType() {
    return delegate.getType().getName();
  }


  @Override
  public ZonedDateTime getCreationDate() {
    return toZonedDateTime(delegate.getCreationDate());
  }

  @Override
  public ZonedDateTime getModificationDate() {
    return toZonedDateTime(delegate.getModificationDate());
  }


  @Override
  public Object get(String propertyName) {
    return proxyFactory.makeProxy(delegate.get(propertyName));
  }


  @Override
  public BlobProxy getBlob(String propertyName) {
    Blob source = delegate.getBlob(propertyName);
    if (source != null) {
      return proxyFactory.makeBlobProxy(source);
    }
    return null;
  }

  @Override
  public Boolean getBoolean(String propertyName) {
    return delegate.getBoolean(propertyName);
  }

  @Override
  public ZonedDateTime getDate(String propertyName) {
    return getZonedDateTime(delegate, propertyName);
  }

  @Override
  public Integer getInteger(String propertyName) {
    return delegate.getInteger(propertyName);
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
    return proxyFactory.makeContentProxyList(delegate.getLinks(propertyName));
  }

  @Override
  public MarkupProxy getMarkup(String propertyName) {
    Markup source = delegate.getMarkup(propertyName);
    if (source != null) {
      return proxyFactory.makeMarkupProxy(source);
    }
    return null;
  }

  @Override
  public String getString(String propertyName) {
    return delegate.getString(propertyName);
  }

  @Override
  public StructProxy getStruct(String propertyName) {
    Struct source = delegate.getStruct(propertyName);
    if (source != null) {
      return proxyFactory.makeStructProxy(source);
    }
    return null;
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
    ContentProxyImpl that = (ContentProxyImpl) o;
    return Objects.equals(delegate, that.delegate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(delegate);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("delegate", delegate)
            .toString();
  }


  /*
   * Package private area for model creation
   */

  Content getDelegate() {
    return delegate;
  }

  Object getModel(String modelName, Object... arguments) {
    return proxyFactory.getRootContext().getModelFactory().createModel(modelName, this, arguments);
  }
}
