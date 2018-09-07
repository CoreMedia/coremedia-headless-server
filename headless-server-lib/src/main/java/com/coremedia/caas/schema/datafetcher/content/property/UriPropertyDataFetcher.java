package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.link.LinkBuilder;
import com.coremedia.caas.service.expression.FieldExpression;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;

public class UriPropertyDataFetcher extends AbstractPropertyDataFetcher {

  public UriPropertyDataFetcher(FieldExpression<?> expression) {
    super(expression, null);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, FieldExpression<?> expression, DataFetchingEnvironment environment) {
    Object target = getProperty(contentProxy, expression, Object.class);
    LinkBuilder linkBuilder = getContext(environment).getProcessingDefinition().getLinkBuilder();
    RootContext rootContext = getContext(environment).getRootContext();
    return linkBuilder.createLink(target, rootContext);
  }
}
