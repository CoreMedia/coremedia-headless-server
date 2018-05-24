package com.coremedia.caas.schema.datafetcher.content.model;

import com.coremedia.caas.services.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;

public class PageGridModelDataFetcher extends AbstractModelDataFetcher {

  public PageGridModelDataFetcher(String sourceName, String modelName) {
    super(sourceName, modelName);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, String modelName, String sourceName, DataFetchingEnvironment environment) {
    return getContext(environment).getRootContext().getModelFactory().createModel(modelName, sourceName, contentProxy);
  }
}
