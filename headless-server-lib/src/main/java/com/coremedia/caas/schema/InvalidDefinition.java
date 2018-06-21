package com.coremedia.caas.schema;

import graphql.GraphQLException;

public abstract class InvalidDefinition extends GraphQLException {

  public InvalidDefinition(String message) {
    super(message);
  }
}
