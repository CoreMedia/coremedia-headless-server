package com.coremedia.caas.schema.field.content.property;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.content.property.ExtendedLinklistPropertyDataFetcher;
import com.coremedia.caas.schema.field.common.AbstractField;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class ExtendedLinklistPropertyField extends AbstractField {

  public ExtendedLinklistPropertyField() {
    super(false, true);
  }


  @Override
  public Collection<GraphQLFieldDefinition> build(SchemaService schemaService) {
    // not customizable for now
    String targetTypeName = "CMLinkable";
    String targetPropertyName = "target";
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcherFactory(decorate(new ExtendedLinklistPropertyDataFetcher(getSourceExpression(schemaService), getFallbackExpressions(schemaService), targetTypeName, targetPropertyName)))
            .build());
  }
}
