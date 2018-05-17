package com.coremedia.caas.schema.datafetcher.content.model.navigation;

import com.coremedia.caas.services.repository.content.ContentProxy;
import com.coremedia.caas.services.repository.content.model.adapter.NavigationAdapter;

import java.util.List;

public class NavigationPathDataFetcher extends NavigationDataFetcher {

  public NavigationPathDataFetcher(String modelName) {
    super(modelName);
  }


  @Override
  protected List<ContentProxy> getData(NavigationAdapter navigationAdapter) {
    return navigationAdapter.getPathToRoot();
  }
}
