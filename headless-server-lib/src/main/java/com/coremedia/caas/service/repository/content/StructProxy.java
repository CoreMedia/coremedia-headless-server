package com.coremedia.caas.service.repository.content;

import java.util.Map;

public interface StructProxy extends ProxyObject {

  Object get(String propertyName);

  Map<String, ?> getProperties();
}
