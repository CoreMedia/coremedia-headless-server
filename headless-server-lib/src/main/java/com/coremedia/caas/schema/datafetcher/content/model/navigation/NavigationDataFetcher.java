package com.coremedia.caas.schema.datafetcher.content.model.navigation;

import com.coremedia.caas.schema.datafetcher.content.model.AbstractModelDataFetcher;
import com.coremedia.caas.services.repository.content.ContentProxy;
import com.coremedia.caas.services.repository.content.model.adapter.NavigationAdapter;

import graphql.schema.DataFetchingEnvironment;

public abstract class NavigationDataFetcher extends AbstractModelDataFetcher {

  public NavigationDataFetcher(String modelName) {
    super(null, modelName);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, String modelName, String sourceName, DataFetchingEnvironment environment) {
    return getData(getContext(environment).getRootContext().getModelFactory().createModel(modelName, sourceName, contentProxy));
  }


  protected abstract Object getData(NavigationAdapter navigationAdapter);
}
