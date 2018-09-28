package com.coremedia.caas.schema;

import com.coremedia.caas.config.loader.LoaderError;

import java.util.List;

public class InvalidRichtextDefinition extends InvalidDefinition {

  public InvalidRichtextDefinition(String message) {
    super(message);
  }

  public InvalidRichtextDefinition(String message, List<LoaderError> loaderErrors) {
    super(message, loaderErrors);
  }
}
