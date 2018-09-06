package com.coremedia.caas.extension.jslt;

import com.coremedia.caas.config.ProcessingDefinition;
import com.coremedia.caas.query.QueryDefinition;
import com.coremedia.caas.server.controller.base.ResponseStatusException;
import com.coremedia.caas.server.service.postprocessor.JsonPostprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schibsted.spt.data.jslt.Expression;
import com.schibsted.spt.data.jslt.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@SuppressWarnings("unused")
public class JsltPostprocessor implements JsonPostprocessor {

  private static final Logger LOG = LoggerFactory.getLogger(JsonPostprocessor.class);


  private ConcurrentHashMap<String, Expression> expressionCache = new ConcurrentHashMap<>();


  @Override
  public Object transform(Map<String, Object> jsonMap, ProcessingDefinition processingDefinition, String queryName, String viewName) {
    // fetch executed query definition
    QueryDefinition queryDefinition = processingDefinition.getQueryRegistry().getDefinition(queryName, viewName);
    // check for query transformation option
    String transformer = queryDefinition.getOption("jslt");
    if (transformer != null) {
      try {
        // run transformation template with name specified in query option
        Expression jslt = expressionCache.computeIfAbsent(transformer, (name) -> Parser.compileResource("jslt/" + name + ".jslt"));
        return jslt.apply(new ObjectMapper().valueToTree(jsonMap));
      } catch (Exception e) {
        LOG.error("JSON transformation failed", e);
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    return null;
  }
}
