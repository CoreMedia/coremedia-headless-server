package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.service.expression.FieldExpression;
import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.caas.service.repository.content.MarkupProxy;

import graphql.schema.DataFetchingEnvironment;

public class MarkupPropertyDataFetcher extends AbstractPropertyDataFetcher {

  public MarkupPropertyDataFetcher(FieldExpression<?> expression) {
    super(expression, null);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, FieldExpression<?> expression, DataFetchingEnvironment environment) {
    MarkupProxy markupProxy = getProperty(contentProxy, expression, MarkupProxy.class);
    if (markupProxy != null) {
      return markupProxy.toString();
    }
    return null;
  }
}
