package com.coremedia.caas.schema.datafetcher.common;

import com.coremedia.caas.schema.util.PropertyUtil;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;

public class KeyedDataFetcher extends AbstractDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(KeyedDataFetcher.class);


  @NotNull
  protected Object getSource(DataFetchingEnvironment environment) {
    return environment.getSource();
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    try {
      String key = environment.getArgument("key");
      Object defaultValue = environment.getArgument("default");
      // source object is either a map or a content object
      Object result = PropertyUtil.get(getSource(environment), key);
      if (result == null) {
        return defaultValue;
      }
      return result;
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return null;
  }
}
