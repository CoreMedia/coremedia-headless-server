package com.coremedia.caas.schema.datafetcher.common;

import com.coremedia.caas.execution.ExecutionContext;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public abstract class AbstractDataFetcher implements DataFetcher {

  protected ExecutionContext getContext(DataFetchingEnvironment environment) {
    return environment.getContext();
  }
}
