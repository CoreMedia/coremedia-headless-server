package com.coremedia.caas.schema.util;

import com.coremedia.cap.content.Content;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ContentUtil {

  public static ZonedDateTime getZonedDateTime(Content target, String propertyName) {
    Calendar date = target.getDate(propertyName);
    return toZonedDateTime(date);
  }

  public static ZonedDateTime toZonedDateTime(Calendar date) {
    if (date instanceof GregorianCalendar) {
      return ((GregorianCalendar) date).toZonedDateTime();
    }
    if (date != null) {
      throw new RuntimeException("Unsupported calendar class: " + date.getClass().getName());
    }
    return null;
  }
}
