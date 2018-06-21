package com.coremedia.caas.server.controller.base;

import org.springframework.http.HttpStatus;

public class ResponseStatusException extends RuntimeException {

  private HttpStatus status;


  public ResponseStatusException(HttpStatus status) {
    this.status = status;
  }

  public ResponseStatusException(HttpStatus status, String message) {
    super(message);
    this.status = status;

  }

  public ResponseStatusException(HttpStatus status, String message, Throwable cause) {
    super(message, cause);
    this.status = status;
  }


  public HttpStatus getStatus() {
    return status;
  }
}
