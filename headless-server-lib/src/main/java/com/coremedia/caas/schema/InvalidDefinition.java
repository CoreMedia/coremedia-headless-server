package com.coremedia.caas.schema;

import com.coremedia.caas.config.loader.LoaderError;

import graphql.GraphQLException;

import java.util.List;

public abstract class InvalidDefinition extends GraphQLException {

  private List<LoaderError> loaderErrors;


  public InvalidDefinition(String message) {
    super(message);
  }

  public InvalidDefinition(String message, List<LoaderError> loaderErrors) {
    super(message);
    this.loaderErrors = loaderErrors;
  }


  public String getDetailMessage() {
    String className = getClass().getName();
    String errorMessage = getLocalizedMessage();
    // format one message string with all loader errors
    StringBuilder builder = new StringBuilder((errorMessage != null) ? (className + ": " + errorMessage) : className);
    if (loaderErrors != null && !loaderErrors.isEmpty()) {
      builder.append('\n').append("Caused by:");
      for (LoaderError error : loaderErrors) {
        builder.append('\n').append(error.toString());
      }
    }
    return builder.toString();
  }
}
