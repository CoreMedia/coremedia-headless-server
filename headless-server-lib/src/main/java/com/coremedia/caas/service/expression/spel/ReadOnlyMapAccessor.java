package com.coremedia.caas.service.expression.spel;

import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypedValue;

import java.util.Map;

public class ReadOnlyMapAccessor implements PropertyAccessor {

  public static final Class<Map> TARGET_CLASS = Map.class;
  public static final Class[] TARGET_CLASSES = new Class[]{TARGET_CLASS};


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
    return new TypedValue(((Map) target).get(name));
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
