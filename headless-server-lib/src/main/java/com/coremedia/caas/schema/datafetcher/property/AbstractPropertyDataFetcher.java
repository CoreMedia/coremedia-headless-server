package com.coremedia.caas.schema.datafetcher.property;

import com.coremedia.caas.schema.datafetcher.common.AbstractDataFetcher;
import com.coremedia.caas.schema.util.PropertyUtil;
import graphql.schema.DataFetchingEnvironment;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractPropertyDataFetcher extends AbstractDataFetcher {

  protected String sourceName;


  public AbstractPropertyDataFetcher(String sourceName) {
    this.sourceName = sourceName;
  }


  protected <E> E getProperty(DataFetchingEnvironment environment) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    // source name is a bean property/path expression
    return PropertyUtil.get(environment.getSource(), sourceName);
  }
}
