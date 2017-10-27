package com.coremedia.caas.schema.datafetcher.property;

import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class BeanPropertyDataFetcher extends AbstractPropertyDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(BeanPropertyDataFetcher.class);


  public BeanPropertyDataFetcher(String sourceName) {
    super(sourceName);
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    try {
      return getProperty(environment);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return null;
  }
}
