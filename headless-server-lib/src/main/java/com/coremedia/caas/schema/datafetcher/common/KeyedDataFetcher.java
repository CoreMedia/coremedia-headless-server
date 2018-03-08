package com.coremedia.caas.schema.datafetcher.common;

import com.coremedia.caas.schema.util.PropertyUtil;

import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import javax.validation.constraints.NotNull;

public class KeyedDataFetcher extends AbstractDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(KeyedDataFetcher.class);


  @NotNull
  protected Object getSource(DataFetchingEnvironment environment) {
    return environment.getSource();
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    String key = getArgument("key", environment);
    if (key != null) {
      try {
        // source object is either a map or a content object
        Object value = PropertyUtil.get(getSource(environment), key);
        if (value != null) {
          return value;
        }
        else {
          return getArgument("default", environment);
        }
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        LOG.error("DataFetcher access failed:", e);
      }
    }
    return null;
  }
}
