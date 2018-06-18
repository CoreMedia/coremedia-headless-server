package com.coremedia.caas.service.repository;

import com.coremedia.caas.service.request.RequestContext;
import com.coremedia.caas.service.security.AccessControl;
import com.coremedia.cap.multisite.Site;

public interface RootContext {

  Site getSite();

  Object getCurrentContext();

  Object getTarget();


  AccessControl getAccessControl();

  ModelFactory getModelFactory();

  ProxyFactory getProxyFactory();


  RequestContext getRequestContext();
}
