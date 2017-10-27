package com.coremedia.caas.execution;

import com.coremedia.caas.config.CaasProcessingDefinition;
import com.coremedia.caas.services.ServiceRegistry;

public class ExecutionContext {

  private CaasProcessingDefinition processingDefinition;
  private ExecutionState executionState;
  private ServiceRegistry serviceRegistry;


  public ExecutionContext(CaasProcessingDefinition processingDefinition, ExecutionState executionState, ServiceRegistry serviceRegistry) {
    this.processingDefinition = processingDefinition;
    this.executionState = executionState;
    this.serviceRegistry = serviceRegistry;
  }


  public CaasProcessingDefinition getProcessingDefinition() {
    return processingDefinition;
  }

  public ExecutionState getExecutionState() {
    return executionState;
  }

  public ServiceRegistry getServiceRegistry() {
    return serviceRegistry;
  }
}
