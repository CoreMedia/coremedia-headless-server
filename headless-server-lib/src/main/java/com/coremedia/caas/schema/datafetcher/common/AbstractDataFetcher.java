package com.coremedia.caas.schema.datafetcher.common;

import com.coremedia.caas.execution.ExecutionContext;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public abstract class AbstractDataFetcher implements DataFetcher {

  protected ExecutionContext getContext(DataFetchingEnvironment environment) {
    return environment.getContext();
  }


  protected <T> T getArgument(String name, DataFetchingEnvironment environment) {
    return getArgumentWithDefault(name, null, environment);
  }

  protected <T> T getArgumentWithDefault(String name, T defaultValue, DataFetchingEnvironment environment) {
    T value = environment.getArgument(name);
    if (value != null) {
      return value;
    }
    return defaultValue;
  }
}
