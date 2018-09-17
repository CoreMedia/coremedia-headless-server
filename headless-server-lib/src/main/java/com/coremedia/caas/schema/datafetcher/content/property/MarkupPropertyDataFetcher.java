package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.service.expression.FieldExpression;
import com.coremedia.caas.service.repository.content.MarkupProxy;

import graphql.schema.DataFetchingEnvironment;

import java.util.List;

public class MarkupPropertyDataFetcher extends AbstractPropertyDataFetcher {

  public MarkupPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions) {
    super(expression, fallbackExpressions);
  }


  @Override
  protected boolean isNullOrEmpty(Object value) {
    return value == null;
  }


  @Override
  protected Object getData(Object proxy, FieldExpression<?> expression, DataFetchingEnvironment environment) {
    MarkupProxy markupProxy = getProperty(proxy, expression, MarkupProxy.class);
    if (markupProxy != null) {
      return markupProxy.toString();
    }
    return null;
  }
}
