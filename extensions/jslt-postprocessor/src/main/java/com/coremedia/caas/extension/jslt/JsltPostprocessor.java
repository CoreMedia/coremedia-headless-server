package com.coremedia.caas.extension.jslt;

import com.coremedia.caas.config.ProcessingDefinition;
import com.coremedia.caas.query.QueryDefinition;
import com.coremedia.caas.server.controller.base.ResponseStatusException;
import com.coremedia.caas.server.controller.interceptor.QueryExecutionInterceptorAdapter;
import com.coremedia.caas.server.service.request.ClientIdentification;
import com.coremedia.caas.service.repository.RootContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schibsted.spt.data.jslt.Expression;
import com.schibsted.spt.data.jslt.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@SuppressWarnings("unused")
public class JsltPostprocessor extends QueryExecutionInterceptorAdapter {

  private static final Logger LOG = LoggerFactory.getLogger(JsltPostprocessor.class);


  private ConcurrentHashMap<String, Expression> expressionCache = new ConcurrentHashMap<>();


  @Override
  public Object postQuery(Object resultData, String tenantId, String siteId, ClientIdentification clientIdentification, RootContext rootContext, ProcessingDefinition processingDefinition, QueryDefinition queryDefinition, Map<String, Object> requestParameters, ServletWebRequest request) {
    // check for query transformation option
    String transformer = queryDefinition.getOption("jslt");
    if (transformer != null) {
      try {
        // run transformation template with name specified in query option
        Expression jslt = expressionCache.computeIfAbsent(transformer, (name) -> Parser.compileResource("jslt/" + name + ".jslt"));
        return jslt.apply(new ObjectMapper().valueToTree(resultData));
      } catch (Exception e) {
        LOG.error("JSON transformation failed", e);
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    return null;
  }
}
