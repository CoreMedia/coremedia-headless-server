package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.service.repository.content.ContentProxy;

import com.google.common.collect.ImmutableList;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.expression.Expression;

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
  protected Object getData(ContentProxy contentProxy, Expression expression, DataFetchingEnvironment environment) {
    SchemaService schema = getContext(environment).getProcessingDefinition().getSchemaService();
    Object property = getProperty(contentProxy, expression, Object.class);
    if (property instanceof Collection) {
      return ((Collection<?>) property).stream().filter(e -> schema.isInstanceOf(e, baseTypeName)).collect(Collectors.toList());
    }
    else if (schema.isInstanceOf(property, baseTypeName)) {
      return ImmutableList.of(property);
    }
    return ImmutableList.of();
  }
}
