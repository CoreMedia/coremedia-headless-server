package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.schema.datafetcher.content.AbstractContentDataFetcher;
import com.coremedia.caas.schema.util.FieldExpressionEvaluator;
import com.coremedia.caas.schema.util.PropertyUtil;
import com.coremedia.caas.service.repository.content.ContentProxy;

import org.springframework.expression.Expression;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractPropertyDataFetcher extends AbstractContentDataFetcher {

  private List<Expression> fallbackExpressions;


  AbstractPropertyDataFetcher(String sourceName, List<String> fallbackSourceNames) {
    super(sourceName);
    this.fallbackExpressions = fallbackSourceNames != null ? fallbackSourceNames.stream().map(FieldExpressionEvaluator::compile).collect(Collectors.toList()) : Collections.emptyList();
  }


  protected <E> E getProperty(ContentProxy contentProxy, Expression expression, Class<E> targetClass) {
    E result = FieldExpressionEvaluator.fetch(expression, contentProxy, targetClass);
    if (PropertyUtil.isNullOrEmpty(result)) {
      for (Expression fallbackExpression : fallbackExpressions) {
        result = FieldExpressionEvaluator.fetch(fallbackExpression, contentProxy, targetClass);
        if (!PropertyUtil.isNullOrEmpty(result)) {
          return result;
        }
      }
      return null;
    }
    return result;
  }
}
