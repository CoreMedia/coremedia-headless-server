package com.coremedia.caas.service.repository.util;

import com.coremedia.cap.content.Content;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ContentUtil {

  public static ZonedDateTime getZonedDateTime(Content target, String propertyName) {
    return getZonedDateTime(target.getDate(propertyName));
  }

  public static ZonedDateTime getZonedDateTime(Calendar calendar) {
    if (calendar instanceof GregorianCalendar) {
      return ((GregorianCalendar) calendar).toZonedDateTime();
    }
    if (calendar != null) {
      throw new RuntimeException("Unsupported calendar class: " + calendar.getClass().getName());
    }
    return null;
  }
}
