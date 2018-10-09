package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.service.expression.FieldExpression;

import graphql.schema.DataFetchingEnvironment;

import java.util.List;

public class StructPropertyDataFetcher extends AbstractPropertyDataFetcher<Object> {

  public StructPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions) {
    super(expression, fallbackExpressions, Object.class);
  }


  @Override
  protected boolean isNullOrEmpty(Object value) {
    return value == null;
  }

  @Override
  protected Object processResult(Object result, DataFetchingEnvironment environment) {
    return result;
  }
}
