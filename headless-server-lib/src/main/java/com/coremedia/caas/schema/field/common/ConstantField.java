package com.coremedia.caas.schema.field.common;

import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.common.ConstantDataFetcher;
import com.coremedia.caas.schema.datafetcher.converter.ConvertingDataFetcher;
import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class ConstantField<E> extends AbstractField {

  private E value;


  public E getValue() {
    return value;
  }

  public void setValue(E value) {
    this.value = value;
  }


  @Override
  public Collection<GraphQLFieldDefinition> build() {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcher(new ConvertingDataFetcher(getTypeName(), new ConstantDataFetcher<E>(getValue())))
            .build());
  }
}
