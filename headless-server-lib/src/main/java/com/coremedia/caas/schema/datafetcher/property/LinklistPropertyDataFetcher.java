package com.coremedia.caas.schema.datafetcher.property;

import com.coremedia.caas.schema.SchemaService;
import com.google.common.collect.ImmutableList;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LinklistPropertyDataFetcher extends AbstractPropertyDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(LinklistPropertyDataFetcher.class);


  private String baseTypeName;


  public LinklistPropertyDataFetcher(String sourceName, List<String> fallbackSourceNames, String baseTypeName) {
    super(sourceName, fallbackSourceNames);
    this.baseTypeName = baseTypeName;
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    SchemaService schema = getContext(environment).getProcessingDefinition().getSchemaService();
    try {
      Object property = getProperty(environment);
      if (property instanceof Collection) {
        return ((Collection<?>) property).stream().filter(e -> schema.isInstanceOf(e, baseTypeName)).collect(Collectors.toList());
      } else if (schema.isInstanceOf(property, baseTypeName)) {
        return ImmutableList.of(property);
      }
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return ImmutableList.of();
  }
}
