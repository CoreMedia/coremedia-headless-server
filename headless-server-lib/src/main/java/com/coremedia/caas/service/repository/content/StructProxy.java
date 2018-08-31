package com.coremedia.caas.service.repository.content;

import java.util.Map;

public interface StructProxy {

  Object get(String propertyName);

  Map<String, ?> getProperties();
}
