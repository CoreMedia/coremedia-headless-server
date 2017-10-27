package com.coremedia.caas.schema.datafetcher.navigation;

import com.coremedia.blueprint.base.tree.TreeRelation;
import com.coremedia.cap.content.Content;
import com.google.common.collect.ImmutableList;
import graphql.schema.DataFetchingEnvironment;

public class NavigationPathDataFetcher extends ContextDataFetcher {

  @Override
  public Object get(DataFetchingEnvironment dataFetchingEnvironment) {
    Object context = super.get(dataFetchingEnvironment);
    if (context instanceof Content) {
      TreeRelation<Content> treeRelation = getContext(dataFetchingEnvironment).getServiceRegistry().getTreeRelation();
      // let the tree relation find the path to the navigation's root
      return treeRelation.pathToRoot((Content) context);
    }
    return ImmutableList.of();
  }
}
