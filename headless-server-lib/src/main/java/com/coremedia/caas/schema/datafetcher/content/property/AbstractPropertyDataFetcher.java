package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.schema.datafetcher.content.AbstractContentDataFetcher;
import com.coremedia.caas.service.expression.FieldExpression;

import java.util.List;

public abstract class AbstractPropertyDataFetcher extends AbstractContentDataFetcher {

  private List<FieldExpression<?>> fallbackExpressions;


  AbstractPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions) {
    super(expression);
    this.fallbackExpressions = fallbackExpressions;
  }


  protected abstract boolean isNullOrEmpty(Object value);


  protected <E> E getProperty(Object proxy, FieldExpression<?> expression, Class<E> targetClass) {
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
    return result;
  }
}
