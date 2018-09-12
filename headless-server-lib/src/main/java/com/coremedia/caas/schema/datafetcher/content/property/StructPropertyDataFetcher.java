package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.service.expression.FieldExpression;
import com.coremedia.caas.service.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;

public class StructPropertyDataFetcher extends AbstractPropertyDataFetcher {

  public StructPropertyDataFetcher(FieldExpression<?> expression) {
    super(expression, null);
  }


  @Override
  protected boolean isNullOrEmpty(Object value) {
    return value == null;
  }


  @Override
  protected Object getData(ContentProxy contentProxy, FieldExpression<?> expression, DataFetchingEnvironment environment) {
    return getProperty(contentProxy, expression, Object.class);
  }
}
