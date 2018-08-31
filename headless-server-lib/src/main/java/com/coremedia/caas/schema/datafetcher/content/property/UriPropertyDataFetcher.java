package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.link.LinkBuilder;
import com.coremedia.caas.service.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.expression.Expression;

public class UriPropertyDataFetcher extends AbstractPropertyDataFetcher {

  public UriPropertyDataFetcher(String sourceName) {
    super(sourceName, null);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, Expression expression, DataFetchingEnvironment environment) {
    Object target = getProperty(contentProxy, expression, Object.class);
    LinkBuilder linkBuilder = getContext(environment).getProcessingDefinition().getLinkBuilder();
    return linkBuilder.createLink(target);
  }
}
