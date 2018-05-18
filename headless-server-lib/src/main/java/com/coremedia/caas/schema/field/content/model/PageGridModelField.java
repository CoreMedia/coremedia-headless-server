package com.coremedia.caas.schema.field.content.model;

import com.coremedia.caas.schema.FieldBuilder;
import com.coremedia.caas.schema.datafetcher.content.model.PageGridModelDataFetcher;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLTypeReference;

import java.util.Collection;

import static com.coremedia.caas.services.repository.ModelFactory.PAGEGRID_MODEL;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class PageGridModelField implements FieldBuilder {

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
    this.sourceName = sourceName;
  }


  @Override
  public Collection<GraphQLFieldDefinition> build() {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(new GraphQLTypeReference("PageGrid"))
            .dataFetcher(new PageGridModelDataFetcher(getSourceName(), PAGEGRID_MODEL))
            .build());
  }
}
