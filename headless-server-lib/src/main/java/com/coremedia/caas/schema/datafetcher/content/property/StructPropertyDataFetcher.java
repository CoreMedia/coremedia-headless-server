package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.service.expression.FieldExpression;

import graphql.schema.DataFetchingEnvironment;

import java.util.List;

public class StructPropertyDataFetcher extends AbstractPropertyDataFetcher {

  public StructPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions) {
    super(expression, fallbackExpressions);
  }


  @Override
  protected boolean isNullOrEmpty(Object value) {
    return value == null;
  }


  @Override
  protected Object getData(Object proxy, FieldExpression<?> expression, DataFetchingEnvironment environment) {
    return getProperty(proxy, expression, Object.class);
  }
}
