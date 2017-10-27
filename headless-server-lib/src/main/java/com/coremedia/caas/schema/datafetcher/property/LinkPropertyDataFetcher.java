package com.coremedia.caas.schema.datafetcher.property;

import com.coremedia.caas.schema.SchemaService;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class LinkPropertyDataFetcher extends AbstractPropertyDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(LinkPropertyDataFetcher.class);


  private String baseTypeName;


  public LinkPropertyDataFetcher(String sourceName, String baseTypeName) {
    super(sourceName);
    this.baseTypeName = baseTypeName;
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    SchemaService schema = getContext(environment).getProcessingDefinition().getSchemaService();
    try {
      Object property = getProperty(environment);
      if (property instanceof Collection) {
        return ((Collection<?>) property).stream().filter(e -> schema.isInstanceOf(e, baseTypeName)).findFirst().orElse(null);
      } else if (schema.isInstanceOf(property, baseTypeName)) {
        return property;
      }
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return null;
  }
}
