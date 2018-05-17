package com.coremedia.caas.schema.datafetcher.content.model.navigation;

import com.coremedia.caas.services.repository.content.ContentProxy;
import com.coremedia.caas.services.repository.content.model.adapter.NavigationAdapter;

public class ContextDataFetcher extends NavigationDataFetcher {

  public ContextDataFetcher(String modelName) {
    super(modelName);
  }


  @Override
  protected ContentProxy getData(NavigationAdapter navigationAdapter) {
    return navigationAdapter.getContext();
  }
}
