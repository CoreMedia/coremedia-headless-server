package com.coremedia.caas.query;

import com.coremedia.caas.schema.InvalidQueryDefinition;
import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.Types;
import com.google.common.base.Ascii;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import graphql.schema.GraphQLSchema;

public class QueryDefinition {

  private String query;
  private String queryName;
  private String viewName;
  private String typeName;

  private SchemaService schema;

  private QuerySchemaLoader querySchemaLoader;


  QueryDefinition(SchemaService schema) {
    this.schema = schema;
  }


  public String getQuery() {
    return query;
  }

  void setQuery(String query) {
    this.query = query;
  }

  String getQueryName() {
    return queryName;
  }

  void setQueryName(String queryName) {
    this.queryName = queryName;
  }

  String getViewName() {
    return viewName;
  }

  void setViewName(String viewName) {
    this.viewName = viewName;
  }

  String getTypeName() {
    return typeName;
  }

  void setTypeName(String typeName) {
    this.typeName = typeName;
  }


  public GraphQLSchema getQuerySchema(Object target) {
    return querySchemaLoader.load(target);
  }


  QueryDefinition resolve() throws InvalidQueryDefinition {
    // validate
    if (Strings.isNullOrEmpty(query) || Strings.isNullOrEmpty(queryName) || Strings.isNullOrEmpty(typeName) || Strings.isNullOrEmpty(viewName)) {
      throw new InvalidQueryDefinition("Invalid query definition: " + this);
    }
    String baseTypeName = Types.getBaseTypeName(typeName);
    if (!schema.hasType(baseTypeName)) {
      throw new InvalidQueryDefinition("Unknown query root type: " + this);
    }
    // create schema loaders
    if (Types.isList(typeName)) {
      querySchemaLoader = new ListQueryLoader(queryName, typeName, schema);
    } else {
      querySchemaLoader = new ObjectQueryLoader(typeName, schema);
    }
    return this;
  }


  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("queryName", queryName)
            .add("viewName", viewName)
            .add("typeName", typeName)
            .add("query", Ascii.truncate(Strings.nullToEmpty(query), 40, "..."))
            .toString();
  }
}
