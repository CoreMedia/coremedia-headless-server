package com.coremedia.caas.query;

import graphql.schema.GraphQLSchema;

interface QuerySchemaLoader {

  GraphQLSchema load(Object target);
}
