package com.coremedia.caas.schema.datafetcher.property;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.richtext.RichtextTransformer;
import com.coremedia.caas.richtext.RichtextTransformerRegistry;
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
  public Object get(DataFetchingEnvironment environment) {
    ExecutionContext context = getContext(environment);
    try {
      Markup markup = getProperty(environment);
      if (markup != null) {
        String view = environment.getArgument("view");
        if (view == null) {
          view = "default";
        }
        RichtextTransformerRegistry registry = context.getProcessingDefinition().getRichtextTransformerRegistry();
        RichtextTransformer transformer = registry.getTransformer(view);
        if (transformer != null) {
          try {
            return transformer.transform(markup, context);
          } catch (Exception e) {
            LOG.error("Richtext transformation failed:", e);
          }
        }
      }
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return null;
  }
}
