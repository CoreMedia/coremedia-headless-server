package com.coremedia.caas.schema.datafetcher.delegate;

import com.coremedia.caas.schema.datafetcher.common.AbstractDataFetcher;
import com.coremedia.caas.service.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BeanDelegatingDataFetcher extends AbstractDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(BeanDelegatingDataFetcher.class);


  private DataFetcherDelegateBean delegate;
  private String sourceName;
  private List<String> fallbackSourceNames;
  private String typeName;


  public BeanDelegatingDataFetcher(DataFetcherDelegateBean delegate, String sourceName, List<String> fallbackSourceNames, String typeName) {
    this.delegate = delegate;
    this.sourceName = sourceName;
    this.fallbackSourceNames = fallbackSourceNames;
    this.typeName = typeName;
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    try {
      Object source = environment.getSource();
      // hard validation to ensure access layer control is not accidentally violated
      if (!(source instanceof ContentProxy)) {
        throw new IllegalArgumentException("Not a ContentProxy: " + source);
      }
      return delegate.get((ContentProxy) source, sourceName, fallbackSourceNames, typeName, environment);
    } catch (Exception e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return null;
  }
}
