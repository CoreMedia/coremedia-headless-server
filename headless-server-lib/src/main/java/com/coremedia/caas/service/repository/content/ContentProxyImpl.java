package com.coremedia.caas.service.repository.content;

import com.coremedia.caas.service.repository.ProxyFactory;
import com.coremedia.cap.common.Blob;
import com.coremedia.cap.content.Content;

import java.util.List;

public class ContentProxyImpl implements ContentProxy {

  private final Content content;
  private final ProxyFactory proxyFactory;


  public ContentProxyImpl(Content content, ProxyFactory proxyFactory) {
    this.content = content;
    this.proxyFactory = proxyFactory;
  }


  @Override
  public boolean isSubtypeOf(String typeName) {
    return content.getType().isSubtypeOf(typeName);
  }


  @Override
  public String getId() {
    return content.getId();
  }

  @Override
  public String getName() {
    return content.getName();
  }

  @Override
  public String getType() {
    return content.getType().getName();
  }


  @Override
  public Object get(String propertyName) {
    return proxyFactory.makeProxy(content.get(propertyName));
  }


  @Override
  public Blob getBlob(String propertyName) {
    return new ContentBlobProxy(content.getBlob(propertyName), this);
  }

  @Override
  public Boolean getBoolean(String propertyName) {
    return content.getBoolean(propertyName);
  }

  @Override
  public Integer getInteger(String propertyName) {
    return content.getInteger(propertyName);
  }

  @Override
  public ContentProxy getLink(String propertyName) {
    List<ContentProxy> contentProxies = getLinks(propertyName);
    if (!contentProxies.isEmpty()) {
      return contentProxies.get(0);
    }
    return null;
  }

  @Override
  public List<ContentProxy> getLinks(String propertyName) {
    return proxyFactory.makeContentProxyList(content.getLinks(propertyName));
  }

  @Override
  public String getString(String propertyName) {
    return content.getString(propertyName);
  }


  /*
   * Package private area for model creation
   */

  Content getContent() {
    return content;
  }

  Object getModel(String modelName) {
    return proxyFactory.getRootContext().getModelFactory().createModel(modelName, null, this);
  }
}
