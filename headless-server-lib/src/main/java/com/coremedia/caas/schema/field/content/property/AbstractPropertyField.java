package com.coremedia.caas.schema.field.content.property;

import com.coremedia.caas.schema.InvalidTypeDefinition;
import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.field.common.AbstractField;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class AbstractPropertyField extends AbstractField {

  public AbstractPropertyField() {
    super(false, false);
  }


  @Override
  public Collection<GraphQLFieldDefinition> build() {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcherFactory(decorate(__ -> { throw new InvalidTypeDefinition("Virtual property definition not replaced: " + getSourceName()); }))
            .build());
  }
}
