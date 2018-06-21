package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.xml.Markup;

import graphql.schema.DataFetchingEnvironment;

import java.lang.reflect.InvocationTargetException;

public class MarkupPropertyDataFetcher extends AbstractPropertyDataFetcher {

  public MarkupPropertyDataFetcher(String sourceName) {
    super(sourceName, null);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, String sourceName, DataFetchingEnvironment environment) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    Markup markup = getProperty(contentProxy, sourceName);
    if (markup != null) {
      return markup.toString();
    }
    return null;
  }
}
