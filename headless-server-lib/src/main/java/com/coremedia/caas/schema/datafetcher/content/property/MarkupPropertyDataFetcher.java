package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.service.expression.FieldExpression;
import com.coremedia.caas.service.repository.content.MarkupProxy;

import graphql.schema.DataFetchingEnvironment;

import java.util.List;

public class MarkupPropertyDataFetcher extends AbstractPropertyDataFetcher<MarkupProxy> {

  public MarkupPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions) {
    super(expression, fallbackExpressions, MarkupProxy.class);
  }


  @Override
  protected boolean isNullOrEmpty(Object value) {
    return value == null;
  }

  @Override
  protected Object processResult(MarkupProxy result, DataFetchingEnvironment environment) {
    if (result != null) {
      return result.toString();
    }
    return null;
  }
}
