package com.coremedia.caas.server.service.postprocessor;

import com.coremedia.caas.config.ProcessingDefinition;

import java.util.Map;

public interface JsonPostprocessor {

  Object transform(Map<String, Object> jsonMap, ProcessingDefinition processingDefinition, String queryName, String viewName);
}
