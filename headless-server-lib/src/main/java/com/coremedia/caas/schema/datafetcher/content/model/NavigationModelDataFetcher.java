package com.coremedia.caas.schema.datafetcher.content.model;

import com.coremedia.caas.schema.util.PropertyUtil;
import com.coremedia.caas.services.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;

import java.lang.reflect.InvocationTargetException;

public class NavigationModelDataFetcher extends AbstractModelDataFetcher {

  public NavigationModelDataFetcher(String sourceName, String modelName) {
    super(sourceName, modelName);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, String modelName, String sourceName, DataFetchingEnvironment environment) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    Object model = getContext(environment).getRootContext().getModelFactory().createModel(modelName, sourceName, contentProxy);
    if (model != null) {
      return PropertyUtil.get(model, sourceName);
    }
    return null;
  }
}
