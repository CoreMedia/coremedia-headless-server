package com.coremedia.caas.schema.field.navigation;

import com.coremedia.caas.schema.FieldBuilder;
import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.navigation.NavigationPathDataFetcher;
import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class NavigationPathField implements FieldBuilder {

  private boolean nonNull;

  private String name;
  private String typeName;


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
            .dataFetcher(new NavigationPathDataFetcher())
            .build());
  }
}
