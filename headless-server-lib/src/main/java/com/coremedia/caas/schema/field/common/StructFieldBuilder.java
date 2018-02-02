package com.coremedia.caas.schema.field.common;

import com.coremedia.caas.schema.datafetcher.converter.ConvertingDataFetcher;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import graphql.Scalars;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLTypeReference;

import java.util.Collection;

public class StructFieldBuilder {

  private String fieldSuffix;
  private DataFetcher dataFetcher;


  public StructFieldBuilder(String fieldSuffix, DataFetcher dataFetcher) {
    this.fieldSuffix = fieldSuffix;
    this.dataFetcher = dataFetcher;
  }


  private String getFieldName(String name) {
    return Strings.isNullOrEmpty(fieldSuffix) ? name : name + fieldSuffix;
  }


  public Collection<GraphQLFieldDefinition> getFields() {
    ImmutableList.Builder<GraphQLFieldDefinition> builder = ImmutableList.builder();

    builder.add(GraphQLFieldDefinition.newFieldDefinition()
            .name(getFieldName("boolean"))
            .type(Scalars.GraphQLBoolean)
            .argument(new GraphQLArgument("key", Scalars.GraphQLString))
            .argument(new GraphQLArgument("default", Scalars.GraphQLBoolean))
            .dataFetcher(new ConvertingDataFetcher("Boolean", dataFetcher))
            .build());

    builder.add(GraphQLFieldDefinition.newFieldDefinition()
            .name(getFieldName("integer"))
            .type(Scalars.GraphQLInt)
            .argument(new GraphQLArgument("key", Scalars.GraphQLString))
            .argument(new GraphQLArgument("default", Scalars.GraphQLInt))
            .dataFetcher(new ConvertingDataFetcher("Integer", dataFetcher))
            .build());

    builder.add(GraphQLFieldDefinition.newFieldDefinition()
            .name(getFieldName("float"))
            .type(Scalars.GraphQLFloat)
            .argument(new GraphQLArgument("key", Scalars.GraphQLString))
            .argument(new GraphQLArgument("default", Scalars.GraphQLFloat))
            .dataFetcher(new ConvertingDataFetcher("Float", dataFetcher))
            .build());

    builder.add(GraphQLFieldDefinition.newFieldDefinition()
            .name(getFieldName("link"))
            .type(new GraphQLTypeReference("Content_"))
            .argument(new GraphQLArgument("key", Scalars.GraphQLString))
            .dataFetcher(dataFetcher)
            .build());

    builder.add(GraphQLFieldDefinition.newFieldDefinition()
            .name(getFieldName("string"))
            .type(Scalars.GraphQLString)
            .argument(new GraphQLArgument("key", Scalars.GraphQLString))
            .argument(new GraphQLArgument("default", Scalars.GraphQLString))
            .dataFetcher(new ConvertingDataFetcher("String", dataFetcher))
            .build());

    return builder.build();
  }
}
