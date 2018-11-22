package com.coremedia.caas.server.controller.interceptor;

import com.coremedia.caas.config.ProcessingDefinition;
import com.coremedia.caas.query.QueryDefinition;
import com.coremedia.caas.server.service.request.ClientIdentification;
import com.coremedia.caas.server.service.request.ContextProperties;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.request.RequestContext;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

@Component
@Order(1)
public class DefaultContextSetupInterceptor extends QueryExecutionInterceptorAdapter {

  @Override
  public boolean preQuery(String tenantId, String siteId, ClientIdentification clientIdentification, RootContext rootContext, ProcessingDefinition processingDefinition, QueryDefinition queryDefinition, Map<String, Object> requestParameters, ServletWebRequest request) {
    RequestContext requestContext = rootContext.getRequestContext();
    // add properties which might be useful during query execution
    requestContext.setProperty(ContextProperties.REQUEST_CLIENT_IDENTIFICATON, clientIdentification);
    requestContext.setProperty(ContextProperties.REQUEST_SITE_ID, siteId);
    requestContext.setProperty(ContextProperties.REQUEST_TENANT_ID, tenantId);
    return true;
  }
}
