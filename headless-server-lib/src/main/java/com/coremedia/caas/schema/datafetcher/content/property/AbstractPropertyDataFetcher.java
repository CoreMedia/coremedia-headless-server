package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.schema.datafetcher.content.AbstractContentDataFetcher;
import com.coremedia.caas.schema.util.FieldExpressionEvaluator;
import com.coremedia.caas.schema.util.PropertyUtil;
import com.coremedia.caas.service.repository.content.ContentProxy;

import org.springframework.expression.Expression;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractPropertyDataFetcher extends AbstractContentDataFetcher {

  private List<Expression> fallbackExpressions;


  AbstractPropertyDataFetcher(String sourceName, List<String> fallbackSourceNames) {
    super(sourceName);
    this.fallbackExpressions = fallbackSourceNames != null ? fallbackSourceNames.stream().map(FieldExpressionEvaluator::compile).collect(Collectors.toList()) : null;
  }


  protected <E> E getProperty(ContentProxy contentProxy, Expression expression, Class<E> targetClass) {
    // source name is a bean property/path expression
    E result = FieldExpressionEvaluator.fetch(expression, contentProxy, targetClass);
    // check for fallback sources if result is empty
    if (fallbackExpressions != null && PropertyUtil.isNullOrEmpty(result)) {
      // iterate manually to skip possibly costly 'isNullOrEmpty' check on last element
      for (int i = 0, c = fallbackExpressions.size() - 1; i <= c; i++) {
        result = FieldExpressionEvaluator.fetch(fallbackExpressions.get(i), contentProxy, targetClass);
        if (i == c || !PropertyUtil.isNullOrEmpty(result)) {
          break;
        }
      }
    }
    return result;
  }
}
