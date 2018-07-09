package com.coremedia.caas.schema.field.common;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.common.ConstantDataFetcher;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class ConstantField<E> extends AbstractField {

  private E value;


  public ConstantField() {
    super(true, false);
  }


  public E getValue() {
    return value;
  }

  public void setValue(E value) {
    this.value = value;
  }


  @Override
  public Collection<GraphQLFieldDefinition> build(SchemaService schemaService) {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcherFactory(decorate(new ConstantDataFetcher<E>(getValue())))
            .build());
  }
}
