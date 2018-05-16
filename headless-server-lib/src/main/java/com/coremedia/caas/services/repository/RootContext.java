package com.coremedia.caas.services.repository;

import com.coremedia.caas.services.request.RequestContext;
import com.coremedia.caas.services.security.AccessControl;
import com.coremedia.cap.content.Content;

public interface RootContext {

  Content getSiteIndicator();

  Content getRootDocument();

  Object getCurrentContext();

  Object getTarget();


  AccessControl getAccessControl();

  ModelFactory getModelFactory();

  ProxyFactory getProxyFactory();


  RequestContext getRequestContext();
}
