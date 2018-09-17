package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.service.expression.FieldExpression;

import graphql.schema.DataFetchingEnvironment;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.coremedia.caas.service.repository.content.util.ContentUtil.isNullOrEmptyLinklist;

public class LinklistPropertyDataFetcher extends AbstractPropertyDataFetcher<Object> {

  private String baseTypeName;


  public LinklistPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions, String baseTypeName) {
    super(expression, fallbackExpressions, Object.class);
    this.baseTypeName = baseTypeName;
  }


  @Override
  protected boolean isNullOrEmpty(Object value) {
    return isNullOrEmptyLinklist(value);
  }

  @Override
  protected Object processResult(Object result, DataFetchingEnvironment environment) {
    SchemaService schema = getContext(environment).getProcessingDefinition().getSchemaService();
    if (result instanceof Collection) {
      return ((Collection<?>) result).stream().filter(e -> schema.isInstanceOf(e, baseTypeName)).collect(Collectors.toList());
    }
    else if (schema.isInstanceOf(result, baseTypeName)) {
      return Collections.singletonList(result);
    }
    return Collections.emptyList();
  }
}
