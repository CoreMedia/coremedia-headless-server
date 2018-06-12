package com.coremedia.caas.service.repository;

import com.coremedia.caas.service.request.RequestContext;
import com.coremedia.caas.service.security.AccessControlViolation;
import com.coremedia.cap.content.Content;

public interface RootContextFactory {

  RootContext createRootContext(Content siteIndicator, Content rootDocument, Object currentContext, Object target, RequestContext requestContext) throws AccessControlViolation;
}
