package com.coremedia.caas.config;

import com.coremedia.caas.link.LinkBuilder;
import com.coremedia.caas.query.QueryDefinition;
import com.coremedia.caas.query.QueryRegistry;
import com.coremedia.caas.richtext.RichtextTransformerRegistry;
import com.coremedia.caas.schema.SchemaService;

import com.google.common.base.MoreObjects;
import graphql.schema.GraphQLSchema;

import java.util.Map;

public class ProcessingDefinition {

  private String name;
  private String description;

  private Map<String, LinkBuilder> linkBuilders;

  private QueryRegistry queryRegistry;
  private RichtextTransformerRegistry richtextTransformerRegistry;
  private SchemaService schemaService;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public Map<String, LinkBuilder> getLinkBuilders() {
    return linkBuilders;
  }

  public void setLinkBuilders(Map<String, LinkBuilder> linkBuilders) {
    this.linkBuilders = linkBuilders;
  }

  public LinkBuilder getLinkBuilder() {
    return getLinkBuilder("default");
  }

  public LinkBuilder getLinkBuilder(String name) {
    return linkBuilders.get(name);
  }


  public QueryRegistry getQueryRegistry() {
    return queryRegistry;
  }

  public void setQueryRegistry(QueryRegistry queryRegistry) {
    this.queryRegistry = queryRegistry;
  }

  public RichtextTransformerRegistry getRichtextTransformerRegistry() {
    return richtextTransformerRegistry;
  }

  public void setRichtextTransformerRegistry(RichtextTransformerRegistry richtextTransformerRegistry) {
    this.richtextTransformerRegistry = richtextTransformerRegistry;
  }

  public SchemaService getSchemaService() {
    return schemaService;
  }

  public void setSchemaService(SchemaService schemaService) {
    this.schemaService = schemaService;
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

  public GraphQLSchema getQuerySchema(Object target, String queryName, String viewName) {
    QueryDefinition definition = queryRegistry.getDefinition(queryName, viewName);
    if (definition != null) {
      return definition.getQuerySchema(target);
    }
    return null;
  }


  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("name", name)
            .add("description", description)
            .toString();
  }
}
