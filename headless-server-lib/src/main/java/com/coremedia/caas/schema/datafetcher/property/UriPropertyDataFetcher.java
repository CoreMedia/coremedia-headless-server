package com.coremedia.caas.schema.datafetcher.property;

import com.coremedia.caas.link.LinkBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class UriPropertyDataFetcher extends AbstractPropertyDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(UriPropertyDataFetcher.class);


  public UriPropertyDataFetcher(String sourceName) {
    super(sourceName, null);
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    try {
      Object target = getProperty(environment);
      LinkBuilder linkBuilder = getContext(environment).getProcessingDefinition().getLinkBuilderRegistry().getBuilder();
      return linkBuilder.createLink(target);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return null;
  }
}
