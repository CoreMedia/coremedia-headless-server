package com.coremedia.caas.execution;

import com.coremedia.caas.config.CaasProcessingDefinition;
import com.coremedia.caas.service.ServiceRegistry;
import com.coremedia.caas.service.repository.RootContext;

public class ExecutionContext {

  private CaasProcessingDefinition processingDefinition;
  private ServiceRegistry serviceRegistry;
  private RootContext rootContext;


  public ExecutionContext(CaasProcessingDefinition processingDefinition, ServiceRegistry serviceRegistry, RootContext rootContext) {
    this.processingDefinition = processingDefinition;
    this.serviceRegistry = serviceRegistry;
    this.rootContext = rootContext;
  }


  public CaasProcessingDefinition getProcessingDefinition() {
    return processingDefinition;
  }

  public ServiceRegistry getServiceRegistry() {
    return serviceRegistry;
  }

  public RootContext getRootContext() {
    return rootContext;
  }
}
