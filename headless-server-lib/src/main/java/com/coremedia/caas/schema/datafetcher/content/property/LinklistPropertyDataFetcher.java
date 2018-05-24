package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.services.repository.content.ContentProxy;

import com.google.common.collect.ImmutableList;
import graphql.schema.DataFetchingEnvironment;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LinklistPropertyDataFetcher extends AbstractPropertyDataFetcher {

  private String baseTypeName;


  public LinklistPropertyDataFetcher(String sourceName, List<String> fallbackSourceNames, String baseTypeName) {
    super(sourceName, fallbackSourceNames);
    this.baseTypeName = baseTypeName;
  }


  @Override
  protected Object getData(ContentProxy contentProxy, String sourceName, DataFetchingEnvironment environment) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    SchemaService schema = getContext(environment).getProcessingDefinition().getSchemaService();
    Object property = getProperty(contentProxy, sourceName);
    if (property instanceof Collection) {
      return ((Collection<?>) property).stream().filter(e -> schema.isInstanceOf(e, baseTypeName)).collect(Collectors.toList());
    }
    else if (schema.isInstanceOf(property, baseTypeName)) {
      return ImmutableList.of(property);
    }
    return ImmutableList.of();
  }
}
