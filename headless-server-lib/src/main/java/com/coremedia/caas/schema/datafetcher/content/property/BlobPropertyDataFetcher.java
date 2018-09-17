package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.service.expression.FieldExpression;
import com.coremedia.caas.service.repository.content.BlobProxy;

import graphql.schema.DataFetchingEnvironment;

import java.util.List;

import static com.coremedia.caas.service.repository.content.util.ContentUtil.isNullOrEmptyBlob;

public class BlobPropertyDataFetcher extends AbstractPropertyDataFetcher<BlobProxy> {

  public BlobPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions) {
    super(expression, fallbackExpressions, BlobProxy.class);
  }


  @Override
  protected boolean isNullOrEmpty(Object value) {
    return isNullOrEmptyBlob(value);
  }

  @Override
  protected Object processResult(BlobProxy result, DataFetchingEnvironment environment) {
    return result;
  }
}
