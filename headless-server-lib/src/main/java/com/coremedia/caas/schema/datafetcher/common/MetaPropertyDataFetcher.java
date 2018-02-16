package com.coremedia.caas.schema.datafetcher.common;

import com.coremedia.caas.schema.SchemaService;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;

import java.util.List;
import java.util.stream.Collectors;

public class MetaPropertyDataFetcher extends AbstractDataFetcher {

  private String sourceName;


  public MetaPropertyDataFetcher(String sourceName) {
    this.sourceName = sourceName;
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    Object source = environment.getSource();
    SchemaService schema = getContext(environment).getProcessingDefinition().getSchemaService();
    GraphQLObjectType objectType = schema.getObjectType(source);
    if (objectType != null) {
      if ("__interfaces".equals(sourceName)) {
        List<GraphQLOutputType> interfaceTypes = objectType.getInterfaces();
        if (interfaceTypes != null) {
          return interfaceTypes.stream().map(GraphQLOutputType::getName).collect(Collectors.toList());
        }
      }
      if ("__baseinterface".equals(sourceName)) {
        GraphQLInterfaceType interfaceType = schema.getInterfaceType(source);
        if (interfaceType != null) {
          return interfaceType.getName();
        }
      }
    }
    return null;
  }
}
