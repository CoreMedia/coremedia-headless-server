package com.coremedia.caas.schema;

import com.coremedia.caas.config.loader.LoaderError;

import java.util.List;

public class InvalidQueryDefinition extends InvalidDefinition {

  public InvalidQueryDefinition(String message) {
    super(message);
  }

  public InvalidQueryDefinition(String message, List<LoaderError> loaderErrors) {
    super(message, loaderErrors);
  }
}
