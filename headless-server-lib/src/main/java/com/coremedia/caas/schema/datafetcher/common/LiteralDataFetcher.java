package com.coremedia.caas.schema.datafetcher.common;

import com.coremedia.caas.schema.type.field.LiteralFields;

import graphql.schema.DataFetchingEnvironment;

public class LiteralDataFetcher extends AbstractDataFetcher {

  @Override
  public Object get(DataFetchingEnvironment environment) {
    return getArgument(LiteralFields.ARGUMENT_VALUE, environment);
  }
}
