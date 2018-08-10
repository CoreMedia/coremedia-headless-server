package com.coremedia.caas.richtext.stax.writer.intermediate.eval;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.link.LinkBuilder;
import com.coremedia.caas.service.repository.ProxyFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EvaluationContext {

  public static Function<EvaluationContext, String> source(String key) {
    return (context) -> context.get(key);
  }


  private EvaluationContext parent;
  private ExecutionContext executionContext;

  private Map<String, String> params = new HashMap<>();


  public EvaluationContext(ExecutionContext executionContext) {
    this.executionContext = executionContext;
  }

  private EvaluationContext(EvaluationContext parent, ExecutionContext executionContext) {
    this.parent = parent;
    this.executionContext = executionContext;
  }


  public EvaluationContext createSubcontext() {
    return new EvaluationContext(this, executionContext);
  }


  public ProxyFactory getProxyFactory() {
    return executionContext.getRootContext().getProxyFactory();
  }

  public LinkBuilder getLinkBuilder() {
    return executionContext.getProcessingDefinition().getLinkBuilder();
  }


  public void add(String key, String value) {
    params.put(key, value);
  }

  public String get(String key) {
    if (params.containsKey(key)) {
      return params.get(key);
    }
    if (parent != null) {
      return parent.get(key);
    }
    return null;
  }
}
