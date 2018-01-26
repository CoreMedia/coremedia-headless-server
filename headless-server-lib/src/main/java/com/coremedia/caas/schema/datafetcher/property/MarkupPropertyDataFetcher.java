package com.coremedia.caas.schema.datafetcher.property;

import com.coremedia.xml.Markup;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class MarkupPropertyDataFetcher extends AbstractPropertyDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(MarkupPropertyDataFetcher.class);


  public MarkupPropertyDataFetcher(String sourceName) {
    super(sourceName, null);
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    try {
      Markup markup = getProperty(environment);
      if (markup != null) {
        return markup.toString();
      }
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return null;
  }
}
