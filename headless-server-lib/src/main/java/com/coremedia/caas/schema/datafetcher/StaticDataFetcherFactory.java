package com.coremedia.caas.schema.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactory;
import graphql.schema.DataFetcherFactoryEnvironment;

public class StaticDataFetcherFactory implements DataFetcherFactory {

  private DataFetcher dataFetcher;


  public StaticDataFetcherFactory(DataFetcher dataFetcher) {
    this.dataFetcher = dataFetcher;
  }


  @Override
  public DataFetcher get(DataFetcherFactoryEnvironment dataFetcherFactoryEnvironment) {
    return dataFetcher;
  }
}
