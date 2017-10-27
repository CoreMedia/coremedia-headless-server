package com.coremedia.caas.schema.datafetcher.navigation;

import com.coremedia.blueprint.base.navigation.context.ContextStrategy;
import com.coremedia.caas.schema.datafetcher.common.AbstractDataFetcher;
import com.coremedia.cap.content.Content;
import graphql.schema.DataFetchingEnvironment;

public class ContextDataFetcher extends AbstractDataFetcher {

  @Override
  public Object get(DataFetchingEnvironment dataFetchingEnvironment) {
    Object source = dataFetchingEnvironment.getSource();
    if (source instanceof Content) {
      Content content = (Content) source;
      if (content.getType().isSubtypeOf("CMNavigation")) {
        // context of a navigation item is the item itself
        return content;
      } else {
        ContextStrategy<Content, Content> contextStrategy = getContext(dataFetchingEnvironment).getServiceRegistry().getContextStrategy();
        Content currentContext = getContext(dataFetchingEnvironment).getExecutionState().getCurrentContext();
        // let the strategy find the context based on the request's current context
        return contextStrategy.findAndSelectContextFor(content, currentContext);
      }
    }
    return null;
  }
}
