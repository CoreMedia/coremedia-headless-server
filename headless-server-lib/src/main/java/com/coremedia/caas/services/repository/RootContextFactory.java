package com.coremedia.caas.services.repository;

import com.coremedia.caas.services.request.RequestContext;
import com.coremedia.caas.services.security.AccessControlViolation;
import com.coremedia.cap.content.Content;

public interface RootContextFactory {

  RootContext createRootContext(Content siteIndicator, Content rootDocument, Object currentContext, Object target, RequestContext requestContext) throws AccessControlViolation;
}
