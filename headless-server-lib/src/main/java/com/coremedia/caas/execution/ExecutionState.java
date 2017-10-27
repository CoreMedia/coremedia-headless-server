package com.coremedia.caas.execution;

import com.coremedia.cap.content.Content;

public class ExecutionState {

  private Content currentContext;


  public ExecutionState(Content currentContext) {
    this.currentContext = currentContext;
  }


  public Content getCurrentContext() {
    return currentContext;
  }
}
