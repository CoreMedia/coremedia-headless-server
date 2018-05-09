package com.coremedia.caas.services.repository.content.model;

import com.coremedia.caas.services.repository.content.ContentProxy;

import java.util.List;

public interface NavigationAdapter {

  ContentProxy getContext();

  List<ContentProxy> getChildren();

  List<ContentProxy> getPathToRoot();
}
