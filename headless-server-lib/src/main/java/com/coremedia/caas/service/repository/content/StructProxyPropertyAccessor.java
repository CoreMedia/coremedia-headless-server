package com.coremedia.caas.service.repository.content;

import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypedValue;

import static com.coremedia.caas.service.repository.content.StructProxyImpl.TARGET_CLASSES;

public class StructProxyPropertyAccessor implements PropertyAccessor {

  @Override
  public Class<?>[] getSpecificTargetClasses() {
    return TARGET_CLASSES;
  }


  @Override
  public boolean canRead(EvaluationContext evaluationContext, Object target, String name) {
    return true;
  }

  @Override
  public TypedValue read(EvaluationContext evaluationContext, Object target, String name) {
    return new TypedValue(((StructProxy) target).get(name));
  }

  @Override
  public boolean canWrite(EvaluationContext evaluationContext, Object target, String name) {
    return false;
  }

  @Override
  public void write(EvaluationContext evaluationContext, Object target, String name, Object newValue) throws AccessException {
    throw new AccessException("Cannot write");
  }
}
