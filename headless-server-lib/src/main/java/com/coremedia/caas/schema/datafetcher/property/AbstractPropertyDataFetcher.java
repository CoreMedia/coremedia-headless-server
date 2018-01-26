package com.coremedia.caas.schema.datafetcher.property;

import com.coremedia.caas.schema.datafetcher.common.AbstractDataFetcher;
import com.coremedia.caas.schema.util.PropertyUtil;
import graphql.schema.DataFetchingEnvironment;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class AbstractPropertyDataFetcher extends AbstractDataFetcher {

  protected String sourceName;
  protected List<String> fallbackSourceNames;


  public AbstractPropertyDataFetcher(String sourceName, List<String> fallbackSourceNames) {
    this.sourceName = sourceName;
    this.fallbackSourceNames = fallbackSourceNames;
  }


  protected <E> E getProperty(DataFetchingEnvironment environment) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    // source name is a bean property/path expression
    E result = PropertyUtil.get(environment.getSource(), sourceName);
    // check for fallback sources if result is empty
    if (fallbackSourceNames != null && PropertyUtil.isNullOrEmpty(result)) {
      // iterate manually to skip possibly costly 'isNullOrEmpty' check on last element
      for (int i = 0, c = fallbackSourceNames.size() - 1; i <= c; i++) {
        result = PropertyUtil.get(environment.getSource(), fallbackSourceNames.get(i));
        if (i == c || !PropertyUtil.isNullOrEmpty(result)) {
          break;
        }
      }
    }
    return result;
  }
}
