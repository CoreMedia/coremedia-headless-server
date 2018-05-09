package com.coremedia.caas.schema.datafetcher.content.model.navigation;

import com.coremedia.caas.services.repository.content.model.NavigationAdapter;

public class ChildrenDataFetcher extends NavigationDataFetcher {

  public ChildrenDataFetcher(String modelName) {
    super(modelName);
  }


  @Override
  protected Object getData(NavigationAdapter navigationAdapter) {
    return navigationAdapter.getChildren();
  }
}
