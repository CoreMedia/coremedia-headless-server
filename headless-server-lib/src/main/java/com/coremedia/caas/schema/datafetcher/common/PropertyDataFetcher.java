package com.coremedia.caas.schema.datafetcher.common;

import com.coremedia.caas.schema.util.FieldExpressionEvaluator;

import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;

public class PropertyDataFetcher extends AbstractDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(PropertyDataFetcher.class);


  private Expression expression;


  public PropertyDataFetcher(String sourceName) {
    this.expression = FieldExpressionEvaluator.compile(sourceName);
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    try {
      return FieldExpressionEvaluator.fetch(expression, environment.getSource());
    } catch (Exception e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return null;
  }
}
