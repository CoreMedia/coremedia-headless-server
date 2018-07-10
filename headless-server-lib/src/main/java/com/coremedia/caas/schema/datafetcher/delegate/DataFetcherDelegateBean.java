package com.coremedia.caas.schema.datafetcher.delegate;

import com.coremedia.caas.service.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;

import java.util.List;

public interface DataFetcherDelegateBean {

  Object get(ContentProxy source, String sourceName, List<String> fallbackSourceNames, String typeName, DataFetchingEnvironment environment) throws Exception;
}
