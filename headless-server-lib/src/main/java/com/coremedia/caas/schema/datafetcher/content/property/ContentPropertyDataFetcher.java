package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.service.expression.FieldExpression;

import graphql.schema.DataFetchingEnvironment;

import java.util.List;

import static com.coremedia.caas.service.repository.content.util.ContentUtil.isNullOrEmptyObject;

public class ContentPropertyDataFetcher extends AbstractPropertyDataFetcher<Object> {

  public ContentPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions) {
    super(expression, fallbackExpressions, Object.class);
  }


  @Override
  protected boolean isNullOrEmpty(Object value) {
    return isNullOrEmptyObject(value);
  }

  @Override
  protected Object processResult(Object result, DataFetchingEnvironment environment) {
    return result;
  }
}
