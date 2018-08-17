package com.coremedia.caas.schema.datafetcher.content.model;

import com.coremedia.caas.schema.datafetcher.common.AbstractDataFetcher;
import com.coremedia.caas.service.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractModelDataFetcher extends AbstractDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractModelDataFetcher.class);


  private String sourceName;
  private String modelName;


  AbstractModelDataFetcher(String sourceName, String modelName) {
    this.sourceName = sourceName;
    this.modelName = modelName;
  }


  @Override
  public final Object get(DataFetchingEnvironment environment) {
    try {
      Object source = environment.getSource();
      // hard validation to ensure access layer control is not accidentally violated
      if (!(source instanceof ContentProxy)) {
        throw new IllegalArgumentException("Not a ContentProxy: " + source);
      }
      return getData((ContentProxy) source, modelName, sourceName, environment);
    } catch (Exception e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return null;
  }


  protected abstract Object getData(ContentProxy contentProxy, String modelName, String sourceName, DataFetchingEnvironment environment);
}
