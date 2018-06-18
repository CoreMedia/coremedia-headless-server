package com.coremedia.caas.service.repository;

import com.coremedia.caas.service.request.RequestContext;
import com.coremedia.caas.service.security.AccessControlViolation;
import com.coremedia.cap.multisite.Site;

public interface RootContextFactory {

  RootContext createRootContext(Site site, Object currentContext, Object target, RequestContext requestContext) throws AccessControlViolation;
}
