package com.coremedia.caas.service.repository.content;

import com.coremedia.caas.schema.type.field.ThisField;

import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypedValue;

import static com.coremedia.caas.service.repository.content.ContentProxyImpl.TARGET_CLASSES;

public class ContentProxyPropertyAccessor implements PropertyAccessor {

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
    ContentProxyImpl contentProxy = (ContentProxyImpl) target;
    switch (name) {
      case "_id":
        return new TypedValue(contentProxy.getId());
      case "_name":
        return new TypedValue(contentProxy.getName());
      case "_type":
        return new TypedValue(contentProxy.getType());
      case "_creationDate":
        return new TypedValue(contentProxy.getCreationDate());
      case "_modificationDate":
        return new TypedValue(contentProxy.getModificationDate());
      case ThisField.NAME:
        return new TypedValue(contentProxy);
      default:
        return new TypedValue(contentProxy.get(name));
    }
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
