package com.coremedia.caas.schema.datafetcher.content;

import com.coremedia.caas.schema.datafetcher.common.AbstractDataFetcher;
import com.coremedia.caas.services.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractContentDataFetcher extends AbstractDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractContentDataFetcher.class);


  private String sourceName;


  public AbstractContentDataFetcher(String sourceName) {
    this.sourceName = sourceName;
  }


  @Override
  public final Object get(DataFetchingEnvironment environment) {
    try {
      Object source = environment.getSource();
      // hard validation to ensure access layer control is not accidentally violated
      if (!(source instanceof ContentProxy)) {
        throw new IllegalArgumentException("Not a ContentProxy: " + source);
      }
      return getData((ContentProxy) source, sourceName, environment);
    } catch (Exception e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return null;
  }


  protected abstract Object getData(ContentProxy contentProxy, String sourceName, DataFetchingEnvironment environment);
}
