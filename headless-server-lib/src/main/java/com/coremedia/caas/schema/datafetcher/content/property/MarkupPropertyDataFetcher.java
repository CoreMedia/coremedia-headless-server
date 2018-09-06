package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.caas.service.repository.content.MarkupProxy;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.expression.Expression;

public class MarkupPropertyDataFetcher extends AbstractPropertyDataFetcher {

  public MarkupPropertyDataFetcher(String sourceName) {
    super(sourceName, null);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, Expression expression, DataFetchingEnvironment environment) {
    MarkupProxy markupProxy = getProperty(contentProxy, expression, MarkupProxy.class);
    if (markupProxy != null) {
      return markupProxy.toString();
    }
    return null;
  }
}
