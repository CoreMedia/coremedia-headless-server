package com.coremedia.caas.schema.datafetcher.property;

import com.coremedia.cap.struct.Struct;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class StructPropertyDataFetcher extends AbstractPropertyDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(StructPropertyDataFetcher.class);


  public StructPropertyDataFetcher(String sourceName) {
    super(sourceName, null);
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    try {
      // struct properties are returned as nested maps
      Struct struct = getProperty(environment);
      if (struct != null) {
        return struct.toNestedMaps();
      }
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return null;
  }
}
