package com.coremedia.caas.services.repository.content.model;

import com.coremedia.caas.services.repository.RootContext;
import com.coremedia.cap.content.Content;

public interface ContentModelFactory<T> {

  String getModelName();

  T createModel(Content content, String propertyPath, RootContext rootContext);
}
