package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.link.LinkBuilder;
import com.coremedia.caas.service.expression.FieldExpression;
import com.coremedia.caas.service.repository.RootContext;

import graphql.schema.DataFetchingEnvironment;

import java.util.List;

public class UriPropertyDataFetcher extends AbstractPropertyDataFetcher {

  public UriPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions) {
    super(expression, fallbackExpressions);
  }


  @Override
  protected boolean isNullOrEmpty(Object value) {
    return value == null;
  }


  @Override
  protected Object getData(Object proxy, FieldExpression<?> expression, DataFetchingEnvironment environment) {
    Object target = getProperty(proxy, expression, Object.class);
    LinkBuilder linkBuilder = getContext(environment).getProcessingDefinition().getLinkBuilder();
    RootContext rootContext = getContext(environment).getRootContext();
    return linkBuilder.createLink(target, rootContext);
  }
}
