package com.coremedia.caas.schema.field.content.model.grid;

import com.coremedia.caas.schema.FieldBuilder;
import com.coremedia.caas.schema.datafetcher.content.model.grid.PageGridDataFetcher;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLTypeReference;

import java.util.Collection;

import static com.coremedia.caas.services.repository.ModelFactory.PAGEGRID_MODEL;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class PageGridField implements FieldBuilder {

  private String name;
  private String sourceName;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSourceName() {
    return sourceName == null ? name : sourceName;
  }

  public void setSourceName(String sourceName) {
    if (sourceName.startsWith("(") && sourceName.endsWith(")")) {
      // service requires direct property name
      sourceName = sourceName.substring(1, sourceName.length() - 1);
    }
    this.sourceName = sourceName;
  }


  @Override
  public Collection<GraphQLFieldDefinition> build() {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(new GraphQLTypeReference("PageGrid"))
            .dataFetcher(new PageGridDataFetcher(getSourceName(), PAGEGRID_MODEL))
            .build());
  }
}
