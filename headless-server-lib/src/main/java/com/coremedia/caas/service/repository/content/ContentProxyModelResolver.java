package com.coremedia.caas.service.repository.content;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.MethodExecutor;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.TypedValue;

import java.util.List;

public class ContentProxyModelResolver implements MethodResolver {

  private static final String MODEL_PREFIX = "$";


  private boolean isQuery;
  private ContentProxyModelFactory contentProxyModelFactory;


  public ContentProxyModelResolver(ContentProxyModelFactory contentProxyModelFactory, boolean isQuery) {
    this.isQuery = isQuery;
    this.contentProxyModelFactory = contentProxyModelFactory;
  }


  @Override
  public MethodExecutor resolve(EvaluationContext context, Object targetObject, String name, List<TypeDescriptor> argumentTypes) {
    if (name.startsWith(MODEL_PREFIX)) {
      final String modelName = name.substring(1);
      // check if applicable to current target
      if (contentProxyModelFactory.isValidModel(targetObject, modelName, isQuery)) {
        return (evaluationContext, target, arguments) -> new TypedValue(contentProxyModelFactory.getModel(target, modelName, arguments));
      }
    }
    return null;
  }
}
