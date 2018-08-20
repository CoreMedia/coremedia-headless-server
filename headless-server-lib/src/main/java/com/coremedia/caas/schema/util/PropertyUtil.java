package com.coremedia.caas.schema.util;

import com.coremedia.xml.Markup;
import com.coremedia.xml.MarkupUtil;

import java.util.Collection;

public class PropertyUtil {

  public static boolean isNullOrEmpty(Object value) {
    return (value == null) ||
           (value instanceof String && ((String) value).isEmpty()) ||
           (value instanceof Collection && ((Collection) value).isEmpty()) ||
           (value instanceof Markup && MarkupUtil.isEmptyRichtext((Markup) value, true));
  }
}
