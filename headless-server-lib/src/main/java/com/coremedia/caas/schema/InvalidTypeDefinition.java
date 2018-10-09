package com.coremedia.caas.schema;

import com.coremedia.caas.config.loader.LoaderError;

import java.util.List;

public class InvalidTypeDefinition extends InvalidDefinition {

  public InvalidTypeDefinition(String message) {
    super(message);
  }

  public InvalidTypeDefinition(String message, List<LoaderError> loaderErrors) {
    super(message, loaderErrors);
  }
}
