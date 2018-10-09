package com.coremedia.caas.service.repository.content;

import com.coremedia.caas.service.repository.ProxyModelFactory;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.repository.content.model.ContentModelFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.coremedia.caas.service.repository.content.ContentProxyImpl.TARGET_CLASS;

public class ContentProxyModelFactory implements ProxyModelFactory {

  private Map<String, ContentModelFactory> modelFactories;


  public ContentProxyModelFactory(List<ContentModelFactory> modelFactories) {
    this.modelFactories = modelFactories.stream().collect(Collectors.toMap(ContentModelFactory::getModelName, Function.identity()));
  }


  @Override
  public boolean appliesTo(RootContext rootContext, String modelName, Object source) {
    return TARGET_CLASS.isInstance(source) && modelFactories.containsKey(modelName);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T createModel(RootContext rootContext, String modelName, Object source, Object... arguments) {
    return (T) modelFactories.get(modelName).createModel(rootContext, TARGET_CLASS.cast(source).getDelegate(), arguments);
  }


  /*
   * Accessor support
   */

  boolean isValidModel(Object source, String modelName, boolean isQuery) {
    return TARGET_CLASS.isInstance(source) && modelFactories.containsKey(modelName) && (!isQuery || modelFactories.get(modelName).isQueryModel());
  }

  Object getModel(Object source, String modelName, Object... arguments) {
    return TARGET_CLASS.cast(source).getModel(modelName, arguments);
  }
}
