package com.coremedia.caas.service.repository.content.model;

import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.cap.content.Content;

public interface ContentModelFactory<T> {

  boolean isExpressionModel();

  String getModelName();

  T createModel(Content content, String propertyPath, RootContext rootContext);
}
