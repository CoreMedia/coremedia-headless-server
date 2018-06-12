package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.service.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;

import java.lang.reflect.InvocationTargetException;

public class StructPropertyDataFetcher extends AbstractPropertyDataFetcher {

  public StructPropertyDataFetcher(String sourceName) {
    super(sourceName, null);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, String sourceName, DataFetchingEnvironment environment) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    return getProperty(contentProxy, sourceName);
  }
}
