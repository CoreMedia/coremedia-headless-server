package com.coremedia.caas.config.loader;

import java.net.URI;

public class LoaderError {

  private String name;
  private URI uri;
  private String errorMessage;


  public LoaderError(String name, URI uri, String errorMessage) {
    this.name = name;
    this.uri = uri;
    this.errorMessage = errorMessage;
  }


  public String getName() {
    return name;
  }

  public URI getUri() {
    return uri;
  }

  public String getErrorMessage() {
    return errorMessage;
  }


  @Override
  public String toString() {
    return "Failed to load " + getName() + "(" + getUri() + "): " + getErrorMessage();
  }
}
