package com.coremedia.caas.schema.datafetcher.content.model;

import com.coremedia.caas.schema.datafetcher.content.AbstractContentDataFetcher;
import com.coremedia.caas.service.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractModelDataFetcher extends AbstractContentDataFetcher {

  private String modelName;


  public AbstractModelDataFetcher(String sourceName, String modelName) {
    super(sourceName);
    this.modelName = modelName;
  }


  @Override
  protected final Object getData(ContentProxy contentProxy, String sourceName, DataFetchingEnvironment environment) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    return getData(contentProxy, modelName, sourceName, environment);
  }


  protected abstract Object getData(ContentProxy contentProxy, String modelName, String sourceName, DataFetchingEnvironment environment) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;
}
