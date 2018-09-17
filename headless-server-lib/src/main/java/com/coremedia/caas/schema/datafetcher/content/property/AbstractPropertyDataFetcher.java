package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.schema.datafetcher.content.AbstractContentDataFetcher;
import com.coremedia.caas.service.expression.FieldExpression;

import graphql.schema.DataFetchingEnvironment;

import java.util.List;

public abstract class AbstractPropertyDataFetcher<E> extends AbstractContentDataFetcher {

  private List<FieldExpression<?>> fallbackExpressions;
  private Class<E> targetClass;


  AbstractPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions, Class<E> targetClass) {
    super(expression);
    this.fallbackExpressions = fallbackExpressions;
    this.targetClass = targetClass;
  }


  protected abstract boolean isNullOrEmpty(Object value);

  protected abstract Object processResult(E result, DataFetchingEnvironment environment);


  @Override
  protected final Object getData(Object proxy, FieldExpression<?> expression, DataFetchingEnvironment environment) {
    // source name is a bean property/path expression
    E result = expression.fetch(proxy, targetClass);
    // check for fallback sources if result is empty
    if (fallbackExpressions != null && isNullOrEmpty(result)) {
      // iterate manually to skip possibly costly 'isNullOrEmpty' check on last element
      for (int i = 0, c = fallbackExpressions.size() - 1; i <= c; i++) {
        result = fallbackExpressions.get(i).fetch(proxy, targetClass);
        if (i == c || !isNullOrEmpty(result)) {
          break;
        }
      }
    }
    return processResult(result, environment);
  }
}
