package com.coremedia.caas.schema.field.common;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.common.ConstantDataFetcher;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class ConstantField extends AbstractField {

  private String expression;


  public ConstantField() {
    super(true, false);
  }


  public String getValue() {
    return expression;
  }

  public void setValue(String expression) {
    this.expression = expression;
  }


  @Override
  public Collection<GraphQLFieldDefinition> build(SchemaService schemaService) {
    Object value = null;
    if (expression != null) {
      // '#{<expression>}' is evaluated immediately
      if (expression.startsWith("#{") && expression.endsWith("}")) {
        value = getExpression(this.expression.substring(2, this.expression.length() - 1), schemaService).fetch(null);
      }
      // '${<expression}' is evaluated at runtime
      else if (expression.startsWith("${") && expression.endsWith("}")) {
        value = getExpression(this.expression.substring(2, this.expression.length() - 1), schemaService);
      }
    }
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcherFactory(decorate(new ConstantDataFetcher(value)))
            .build());
  }
}
