package com.coremedia.caas.schema.field.content.property;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.content.property.StructPropertyDataFetcher;
import com.coremedia.caas.schema.field.common.AbstractField;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class StructPropertyField extends AbstractField {

  public StructPropertyField() {
    super(true, true);
  }


  @Override
  public Collection<GraphQLFieldDefinition> build(SchemaService schemaService) {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcherFactory(decorate(new StructPropertyDataFetcher(getSourceExpression(schemaService))))
            .build());
  }
}
