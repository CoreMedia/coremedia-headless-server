package com.coremedia.caas.schema.field.content.property;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.content.property.LinklistPropertyDataFetcher;
import com.coremedia.caas.schema.field.common.AbstractField;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class LinklistPropertyField extends AbstractField {

  public LinklistPropertyField() {
    super(false, true);
  }


  @Override
  public Collection<GraphQLFieldDefinition> build(SchemaService schemaService) {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcherFactory(decorate(new LinklistPropertyDataFetcher(getSourceExpression(schemaService), getFallbackExpressions(schemaService), Types.getBaseTypeName(getTypeName()))))
            .build());
  }
}
