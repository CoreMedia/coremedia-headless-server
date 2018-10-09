package com.coremedia.caas.service.repository.content.model;

import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.cap.content.Content;

public interface ContentModelFactory<T> {

  boolean isQueryModel();

  String getModelName();

  T createModel(RootContext rootContext, Content content, Object... arguments);
}
