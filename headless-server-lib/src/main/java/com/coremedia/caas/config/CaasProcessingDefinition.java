package com.coremedia.caas.config;

import com.coremedia.caas.link.LinkBuilderRegistry;
import com.coremedia.caas.query.QueryDefinition;
import com.coremedia.caas.query.QueryRegistry;
import com.coremedia.caas.richtext.RichtextTransformerRegistry;
import com.coremedia.caas.schema.SchemaService;
import graphql.schema.GraphQLSchema;

public class CaasProcessingDefinition {

  private String name;
  private SchemaService schemaService;
  private LinkBuilderRegistry linkBuilderRegistry;
  private RichtextTransformerRegistry richtextTransformerRegistry;
  private QueryRegistry queryRegistry;


  public CaasProcessingDefinition(String name, SchemaService schemaService, QueryRegistry queryRegistry, LinkBuilderRegistry linkBuilderRegistry, RichtextTransformerRegistry richtextTransformerRegistry) {
    this.name = name;
    this.schemaService = schemaService;
    this.queryRegistry = queryRegistry;
    this.linkBuilderRegistry = linkBuilderRegistry;
    this.richtextTransformerRegistry = richtextTransformerRegistry;
  }


  public String getName() {
    return name;
  }


  public SchemaService getSchemaService() {
    return schemaService;
  }

  public QueryRegistry getQueryRegistry() {
    return queryRegistry;
  }

  public LinkBuilderRegistry getLinkBuilderRegistry() {
    return linkBuilderRegistry;
  }

  public RichtextTransformerRegistry getRichtextTransformerRegistry() {
    return richtextTransformerRegistry;
  }


  public boolean hasQueryDefinition(String queryName, String viewName) {
    return queryRegistry.hasDefinition(queryName, viewName);
  }

  public String getQuery(String queryName, String viewName) {
    QueryDefinition definition = queryRegistry.getDefinition(queryName, viewName);
    if (definition != null) {
      return definition.getQuery();
    }
    return null;
  }

  public GraphQLSchema getQuerySchema(String queryName, String viewName) {
    QueryDefinition definition = queryRegistry.getDefinition(queryName, viewName);
    if (definition != null) {
      return definition.getQuerySchema();
    }
    return null;
  }
}
