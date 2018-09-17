package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.service.expression.FieldExpression;

import graphql.schema.DataFetchingEnvironment;

import java.util.Collection;
import java.util.List;

import static com.coremedia.caas.service.repository.content.util.ContentUtil.isNullOrEmptyLinklist;

public class LinkPropertyDataFetcher extends AbstractPropertyDataFetcher<Object> {

  private String baseTypeName;


  public LinkPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions, String baseTypeName) {
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
      return ((Collection<?>) result).stream().filter(e -> schema.isInstanceOf(e, baseTypeName)).findFirst().orElse(null);
    }
    else if (schema.isInstanceOf(result, baseTypeName)) {
      return result;
    }
    return null;
  }
}
