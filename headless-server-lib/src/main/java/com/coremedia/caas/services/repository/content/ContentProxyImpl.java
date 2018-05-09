package com.coremedia.caas.services.repository.content;

import com.coremedia.caas.services.repository.ProxyFactory;
import com.coremedia.cap.content.Content;

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
