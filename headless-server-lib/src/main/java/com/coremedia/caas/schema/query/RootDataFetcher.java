package com.coremedia.caas.schema.query;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

@Component
public class RootDataFetcher implements DataFetcher {

  @Override
  public Object get(DataFetchingEnvironment environment) {
    return environment.getRoot();
  }
}
