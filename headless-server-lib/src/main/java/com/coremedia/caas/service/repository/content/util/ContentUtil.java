package com.coremedia.caas.service.repository.content.util;

import com.coremedia.caas.service.repository.content.MarkupProxy;
import com.coremedia.cap.content.Content;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

public class ContentUtil {

  public static boolean isNullOrEmpty(Object value) {
    return (value == null) ||
           (value instanceof String && ((String) value).isEmpty()) ||
           (value instanceof Collection && ((Collection) value).isEmpty()) ||
           (value instanceof MarkupProxy && ((MarkupProxy) value).isEmpty());
  }


  public static ZonedDateTime getZonedDateTime(Content target, String propertyName) {
    return toZonedDateTime(target.getDate(propertyName));
  }

  public static ZonedDateTime toZonedDateTime(Calendar calendar) {
    if (calendar instanceof GregorianCalendar) {
      return ((GregorianCalendar) calendar).toZonedDateTime();
    }
    if (calendar != null) {
      throw new RuntimeException("Unsupported calendar class: " + calendar.getClass().getName());
    }
    return null;
  }
}
