package com.coremedia.caas.schema.datafetcher.common;

import com.coremedia.caas.service.expression.FieldExpression;

import graphql.schema.DataFetchingEnvironment;

public class ConstantDataFetcher extends AbstractDataFetcher {

  private Object value;


  public ConstantDataFetcher(Object value) {
    this.value = value;
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    if (value instanceof FieldExpression) {
      return ((FieldExpression) value).fetch(environment.getSource());
    }
    return value;
  }
}
