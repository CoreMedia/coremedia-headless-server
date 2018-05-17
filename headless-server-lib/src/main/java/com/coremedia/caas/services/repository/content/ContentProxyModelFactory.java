package com.coremedia.caas.services.repository.content;

import com.coremedia.caas.services.repository.ProxyModelFactory;
import com.coremedia.caas.services.repository.RootContext;
import com.coremedia.caas.services.repository.content.model.ContentModelFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ContentProxyModelFactory implements ProxyModelFactory {

  private Map<String, ContentModelFactory> modelFactories;


  public ContentProxyModelFactory(List<ContentModelFactory> modelFactories) {
    this.modelFactories = modelFactories.stream().collect(Collectors.toMap(ContentModelFactory::getModelName, Function.identity()));
  }


  @Override
  public boolean appliesTo(String modelName, String propertyPath, Object source, RootContext rootContext) {
    return (source instanceof ContentProxyImpl) && modelFactories.containsKey(modelName);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T createModel(String modelName, String propertyPath, Object source, RootContext rootContext) {
    return (T) modelFactories.get(modelName).createModel(((ContentProxyImpl) source).getContent(), propertyPath, rootContext);
  }
}
