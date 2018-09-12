package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.service.expression.FieldExpression;
import com.coremedia.caas.service.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;

import java.util.Collection;
import java.util.List;

import static com.coremedia.caas.service.repository.content.util.ContentUtil.isNullOrEmptyLinklist;

public class LinkPropertyDataFetcher extends AbstractPropertyDataFetcher {

  private String baseTypeName;


  public LinkPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions, String baseTypeName) {
    super(expression, fallbackExpressions);
    this.baseTypeName = baseTypeName;
  }


  @Override
  protected boolean isNullOrEmpty(Object value) {
    return isNullOrEmptyLinklist(value);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, FieldExpression<?> expression, DataFetchingEnvironment environment) {
    SchemaService schema = getContext(environment).getProcessingDefinition().getSchemaService();
    Object property = getProperty(contentProxy, expression, Object.class);
    if (property instanceof Collection) {
      return ((Collection<?>) property).stream().filter(e -> schema.isInstanceOf(e, baseTypeName)).findFirst().orElse(null);
    }
    else if (schema.isInstanceOf(property, baseTypeName)) {
      return property;
    }
    return null;
  }
}
