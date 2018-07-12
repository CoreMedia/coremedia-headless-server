package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.link.LinkBuilder;
import com.coremedia.caas.service.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;

import java.lang.reflect.InvocationTargetException;

public class UriPropertyDataFetcher extends AbstractPropertyDataFetcher {

  public UriPropertyDataFetcher(String sourceName) {
    super(sourceName, null);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, String sourceName, DataFetchingEnvironment environment) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    Object target = getProperty(contentProxy, sourceName);
    LinkBuilder linkBuilder = getContext(environment).getProcessingDefinition().getLinkBuilder();
    return linkBuilder.createLink(target);
  }
}
