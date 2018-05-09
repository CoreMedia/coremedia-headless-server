package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.schema.datafetcher.content.AbstractContentDataFetcher;
import com.coremedia.caas.schema.util.PropertyUtil;
import com.coremedia.caas.services.repository.content.ContentProxy;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class AbstractPropertyDataFetcher extends AbstractContentDataFetcher {

  private List<String> fallbackSourceNames;


  public AbstractPropertyDataFetcher(String sourceName, List<String> fallbackSourceNames) {
    super(sourceName);
    this.fallbackSourceNames = fallbackSourceNames;
  }


  protected <E> E getProperty(ContentProxy contentProxy, String sourceName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    // source name is a bean property/path expression
    E result = PropertyUtil.get(contentProxy, sourceName);
    // check for fallback sources if result is empty
    if (fallbackSourceNames != null && PropertyUtil.isNullOrEmpty(result)) {
      // iterate manually to skip possibly costly 'isNullOrEmpty' check on last element
      for (int i = 0, c = fallbackSourceNames.size() - 1; i <= c; i++) {
        result = PropertyUtil.get(contentProxy, fallbackSourceNames.get(i));
        if (i == c || !PropertyUtil.isNullOrEmpty(result)) {
          break;
        }
      }
    }
    return result;
  }
}
