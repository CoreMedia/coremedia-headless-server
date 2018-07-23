package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.richtext.RichtextTransformer;
import com.coremedia.caas.richtext.RichtextTransformerRegistry;
import com.coremedia.caas.richtext.output.StringOutputFactory;
import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.xml.Markup;

import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class RichtextPropertyDataFetcher extends AbstractPropertyDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(RichtextPropertyDataFetcher.class);


  public RichtextPropertyDataFetcher(String sourceName, List<String> fallbackSourceNames) {
    super(sourceName, fallbackSourceNames);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, String sourceName, DataFetchingEnvironment environment) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    ExecutionContext context = getContext(environment);
    Markup markup = getProperty(contentProxy, sourceName);
    if (markup != null) {
      String view = getArgumentWithDefault("view", "default", environment);
      // get matching transformer and convert markup
      RichtextTransformerRegistry registry = context.getProcessingDefinition().getRichtextTransformerRegistry();
      RichtextTransformer transformer = registry.getTransformer(view);
      if (transformer != null) {
        try {
          return transformer.transform(markup, new StringOutputFactory(), context);
        } catch (Exception e) {
          LOG.error("Richtext transformation failed:", e);
        }
      }
    }
    return null;
  }
}
