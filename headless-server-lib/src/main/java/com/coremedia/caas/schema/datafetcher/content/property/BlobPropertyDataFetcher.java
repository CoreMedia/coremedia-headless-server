package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.service.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.expression.Expression;

import java.util.List;

public class BlobPropertyDataFetcher extends AbstractPropertyDataFetcher {

  public BlobPropertyDataFetcher(String sourceName, List<String> fallbackSourceNames) {
    super(sourceName, fallbackSourceNames);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, Expression expression, DataFetchingEnvironment environment) {
    return getProperty(contentProxy, expression, Object.class);
  }
}
