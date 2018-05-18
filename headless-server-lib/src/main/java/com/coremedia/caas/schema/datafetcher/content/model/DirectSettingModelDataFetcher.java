package com.coremedia.caas.schema.datafetcher.content.model;

import com.coremedia.caas.services.repository.content.ContentProxy;
import com.coremedia.caas.services.repository.content.model.adapter.SettingsAdapter;

import graphql.schema.DataFetchingEnvironment;

public class DirectSettingModelDataFetcher extends AbstractModelDataFetcher {

  private Object defaultValue;


  public DirectSettingModelDataFetcher(String sourceName, String modelName, Object defaultValue) {
    super(sourceName, modelName);
    this.defaultValue = defaultValue;
  }


  @Override
  protected Object getData(ContentProxy contentProxy, String modelName, String sourceName, DataFetchingEnvironment environment) {
    SettingsAdapter model = getContext(environment).getRootContext().getModelFactory().createModel(modelName, sourceName, contentProxy);
    if (model != null) {
      return model.getSetting(sourceName, defaultValue);
    }
    return null;
  }
}
