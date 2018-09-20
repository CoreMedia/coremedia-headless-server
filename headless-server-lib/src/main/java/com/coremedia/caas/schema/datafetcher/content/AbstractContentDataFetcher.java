package com.coremedia.caas.schema.datafetcher.content;

import com.coremedia.caas.schema.datafetcher.common.AbstractDataFetcher;
import com.coremedia.caas.service.expression.FieldExpression;
import com.coremedia.caas.service.repository.content.ProxyObject;

import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractContentDataFetcher extends AbstractDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractContentDataFetcher.class);


  private FieldExpression<?> expression;


  public AbstractContentDataFetcher(FieldExpression<?> expression) {
    this.expression = expression;
  }


  @Override
  public final Object get(DataFetchingEnvironment environment) {
    try {
      Object source = environment.getSource();
      // hard validation to ensure access layer control is not accidentally violated
      if (!(source instanceof ProxyObject)) {
        throw new IllegalArgumentException("Not a Proxy: " + source);
      }
      return getData(source, expression, environment);
    } catch (Exception e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return null;
  }


  protected abstract Object getData(Object proxy, FieldExpression<?> expression, DataFetchingEnvironment environment);
}
