package com.coremedia.caas.service.expression;

import java.util.Map;

public interface RequestParameterAccessor {

  Object getParam(String name);

  Object getParams(String name);

  Map<String, Object> getParamMap();
}
