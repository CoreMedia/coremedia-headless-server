package com.coremedia.caas.services.repository.content;

import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypedValue;

public class ContentProxyModelAccessor implements PropertyAccessor {

  private ContentProxyModelFactory contentProxyModelFactory;


  public ContentProxyModelAccessor(ContentProxyModelFactory contentProxyModelFactory) {
    this.contentProxyModelFactory = contentProxyModelFactory;
  }


  @Override
  public Class<?>[] getSpecificTargetClasses() {
    return contentProxyModelFactory.getTargetClasses();
  }


  @Override
  public boolean canRead(EvaluationContext context, Object target, String name) {
    return contentProxyModelFactory.isExpressionModel(target, name);
  }

  @Override
  public TypedValue read(EvaluationContext context, Object target, String name) {
    return new TypedValue(contentProxyModelFactory.getModel(target, name));
  }

  @Override
  public boolean canWrite(EvaluationContext context, Object target, String name) {
    return false;
  }

  @Override
  public void write(EvaluationContext context, Object target, String name, Object newValue) throws AccessException {
    throw new AccessException("Cannot write");
  }
}
