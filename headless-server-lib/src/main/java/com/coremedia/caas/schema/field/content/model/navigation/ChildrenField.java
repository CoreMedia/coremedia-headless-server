package com.coremedia.caas.schema.field.content.model.navigation;

import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.content.model.navigation.ChildrenDataFetcher;
import com.coremedia.caas.schema.field.common.AbstractField;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static com.coremedia.caas.services.repository.ModelFactory.NAVIGATION_MODEL;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class ChildrenField extends AbstractField {

  private boolean nonNull;

  private String name;
  private String typeName;


  public ChildrenField() {
    super(true, true);
  }


  public boolean isNonNull() {
    return nonNull;
  }

  public void setNonNull(boolean nonNull) {
    this.nonNull = nonNull;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }


  @Override
  public Collection<GraphQLFieldDefinition> build() {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcherFactory(decorate(new ChildrenDataFetcher(NAVIGATION_MODEL)))
            .build());
  }
}
