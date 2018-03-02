package com.coremedia.caas.schema.directive;

import com.coremedia.caas.schema.datafetcher.common.AbstractDataFetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import javax.validation.constraints.NotNull;

public class NoopDirectiveDataFetcher extends AbstractDataFetcher {

  private DataFetcher delegate;


  public NoopDirectiveDataFetcher(@NotNull DataFetcher delegate) {
    this.delegate = delegate;
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    return delegate.get(environment);
  }
}
