package com.coremedia.caas.schema.datafetcher.common;

import com.coremedia.caas.service.expression.FieldExpression;

import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyDataFetcher extends AbstractDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(PropertyDataFetcher.class);


  private FieldExpression<?> expression;


  public PropertyDataFetcher(FieldExpression<?> expression) {
    this.expression = expression;
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    try {
      return expression.fetch(environment.getSource());
    } catch (Exception e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return null;
  }
}
