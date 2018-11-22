package com.coremedia.caas.server.controller.interceptor;

import com.coremedia.caas.config.ProcessingDefinition;
import com.coremedia.caas.query.QueryDefinition;
import com.coremedia.caas.server.service.request.ClientIdentification;
import com.coremedia.caas.service.repository.RootContext;

import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

public class QueryExecutionInterceptorAdapter implements QueryExecutionInterceptor {

  @Override
  public boolean preQuery(String tenantId, String siteId, ClientIdentification clientIdentification, RootContext rootContext, ProcessingDefinition processingDefinition, QueryDefinition queryDefinition, Map<String, Object> requestParameters, ServletWebRequest request) {
    return true;
  }

  @Override
  public Object postQuery(Object resultData, String tenantId, String siteId, ClientIdentification clientIdentification, RootContext rootContext, ProcessingDefinition processingDefinition, QueryDefinition queryDefinition, Map<String, Object> requestParameters, ServletWebRequest request) {
    return null;
  }
}
