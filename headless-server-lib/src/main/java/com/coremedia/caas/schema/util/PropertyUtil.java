package com.coremedia.caas.schema.util;

import com.coremedia.caas.service.repository.content.MarkupProxy;

import java.util.Collection;

public class PropertyUtil {

  public static boolean isNullOrEmpty(Object value) {
    return (value == null) ||
           (value instanceof String && ((String) value).isEmpty()) ||
           (value instanceof Collection && ((Collection) value).isEmpty()) ||
           (value instanceof MarkupProxy && ((MarkupProxy) value).isEmpty());
  }
}
