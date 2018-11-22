package com.coremedia.caas.server.service.expression;

import com.coremedia.caas.server.service.request.GlobalParameters;
import com.coremedia.caas.service.expression.RequestParameterAccessor;

import org.springframework.web.context.request.ServletWebRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestParameterAccessorImpl implements RequestParameterAccessor {

  private Map<String, List<Object>> parameterMap;


  public RequestParameterAccessorImpl(ServletWebRequest request) {
    this.parameterMap = request.getParameterMap().entrySet().stream()
            .filter(e -> !GlobalParameters.GLOBAL_BLACKLIST.contains(e.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, e -> Arrays.asList(e.getValue())));
  }


  @Override
  public Object getParam(String name) {
    if (parameterMap.containsKey(name)) {
      return parameterMap.get(name).get(0);
    }
    return null;
  }

  @Override
  public List<Object> getParams(String name) {
    if (parameterMap.containsKey(name)) {
      return parameterMap.get(name);
    }
    return null;
  }

  @Override
  public Map<String, Object> getParamMap() {
    return parameterMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
