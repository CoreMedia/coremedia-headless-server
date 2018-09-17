package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.service.expression.FieldExpression;

import graphql.schema.DataFetchingEnvironment;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.coremedia.caas.service.repository.content.util.ContentUtil.isNullOrEmptyLinklist;

public class LinklistPropertyDataFetcher extends AbstractPropertyDataFetcher {

  private String baseTypeName;


  public LinklistPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions, String baseTypeName) {
    super(expression, fallbackExpressions);
    this.baseTypeName = baseTypeName;
  }


  @Override
  protected boolean isNullOrEmpty(Object value) {
    return isNullOrEmptyLinklist(value);
  }


  @Override
  protected Object getData(Object proxy, FieldExpression<?> expression, DataFetchingEnvironment environment) {
    SchemaService schema = getContext(environment).getProcessingDefinition().getSchemaService();
    Object property = getProperty(proxy, expression, Object.class);
    if (property instanceof Collection) {
      return ((Collection<?>) property).stream().filter(e -> schema.isInstanceOf(e, baseTypeName)).collect(Collectors.toList());
    }
    else if (schema.isInstanceOf(property, baseTypeName)) {
      return Collections.singletonList(property);
    }
    return Collections.emptyList();
  }
}
