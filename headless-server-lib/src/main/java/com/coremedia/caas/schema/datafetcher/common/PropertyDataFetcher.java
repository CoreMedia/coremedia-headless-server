package com.coremedia.caas.schema.datafetcher.common;

import com.coremedia.caas.schema.util.PropertyUtil;

import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class PropertyDataFetcher extends AbstractDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(PropertyDataFetcher.class);


  private String sourceName;


  public PropertyDataFetcher(String sourceName) {
    this.sourceName = sourceName;
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    try {
      return PropertyUtil.get(environment.getSource(), sourceName);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return null;
  }
}
