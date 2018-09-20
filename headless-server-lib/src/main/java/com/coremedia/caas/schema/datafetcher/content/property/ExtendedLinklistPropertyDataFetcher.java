package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.service.expression.FieldExpression;
import com.coremedia.caas.service.repository.content.StructProxy;

import graphql.schema.DataFetchingEnvironment;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ExtendedLinklistPropertyDataFetcher extends AbstractPropertyDataFetcher<Object> {

  private String targetTypeName;
  private String targetPropertyName;


  public ExtendedLinklistPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions, String targetTypeName, String targetPropertyName) {
    super(expression, fallbackExpressions, Object.class);
    this.targetTypeName = targetTypeName;
    this.targetPropertyName = targetPropertyName;
  }


  @Override
  protected boolean isNullOrEmpty(Object value) {
    return value == null;
  }

  @Override
  protected Object processResult(Object result, DataFetchingEnvironment environment) {
    SchemaService schema = getContext(environment).getProcessingDefinition().getSchemaService();
    if (result instanceof Collection) {
      return ((Collection<?>) result).stream().filter(e -> isInstanceOf(e, schema)).collect(Collectors.toList());
    }
    else if (isInstanceOf(result, schema)) {
      return Collections.singletonList(result);
    }
    return Collections.emptyList();
  }


  private boolean isInstanceOf(Object extendedLink, SchemaService schema) {
    if (extendedLink instanceof StructProxy) {
      Object target = ((StructProxy) extendedLink).get(targetPropertyName);
      return schema.isInstanceOf(target, targetTypeName);
    }
    return false;
  }
}
