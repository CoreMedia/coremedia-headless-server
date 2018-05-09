package com.coremedia.caas.schema.datafetcher.content.model.grid;

import com.coremedia.caas.schema.datafetcher.content.model.AbstractModelDataFetcher;
import com.coremedia.caas.services.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;

public class PageGridDataFetcher extends AbstractModelDataFetcher {

  public PageGridDataFetcher(String sourceName, String modelName) {
    super(sourceName, modelName);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, String modelName, String sourceName, DataFetchingEnvironment environment) {
    return getContext(environment).getRootContext().getModelFactory().createModel(modelName, sourceName, contentProxy);
  }
}
