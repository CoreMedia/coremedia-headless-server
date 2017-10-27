package com.coremedia.caas.schema.datafetcher.common;

import graphql.schema.DataFetchingEnvironment;

public class ConstantDataFetcher<E> extends AbstractDataFetcher {

  private E value;


  public ConstantDataFetcher(E value) {
    this.value = value;
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    return value;
  }
}
