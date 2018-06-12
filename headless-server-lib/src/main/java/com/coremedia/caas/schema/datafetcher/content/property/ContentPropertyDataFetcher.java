package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.service.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ContentPropertyDataFetcher extends AbstractPropertyDataFetcher {

  public ContentPropertyDataFetcher(String sourceName, List<String> fallbackSourceNames) {
    super(sourceName, fallbackSourceNames);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, String sourceName, DataFetchingEnvironment environment) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    return getProperty(contentProxy, sourceName);
  }
}
