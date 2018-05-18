package com.coremedia.caas.schema.datafetcher.content.model;

import com.coremedia.caas.services.repository.content.ContentProxy;
import com.coremedia.caas.services.repository.content.model.adapter.SettingsAdapter;

import com.google.common.base.Splitter;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;

public class DirectSettingModelDataFetcher extends AbstractModelDataFetcher {

  public DirectSettingModelDataFetcher(String modelName) {
    super(null, modelName);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, String modelName, String sourceName, DataFetchingEnvironment environment) {
    String key = getArgument("key", environment);
    Object defaultValue = getArgument("default", environment);
    if (key != null) {
      List<String> keys = Splitter.on(getArgumentWithDefault("separator", '/', environment)).omitEmptyStrings().splitToList(key);
      return ((SettingsAdapter) getContext(environment).getRootContext().getModelFactory().createModel(modelName, sourceName, contentProxy)).getSetting(keys, defaultValue);
    }
    return null;
  }
}
