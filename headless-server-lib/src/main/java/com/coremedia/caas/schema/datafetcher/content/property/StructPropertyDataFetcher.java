package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.service.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.expression.Expression;

public class StructPropertyDataFetcher extends AbstractPropertyDataFetcher {

  public StructPropertyDataFetcher(String sourceName) {
    super(sourceName, null);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, Expression expression, DataFetchingEnvironment environment) {
    return getProperty(contentProxy, expression, Object.class);
  }
}
