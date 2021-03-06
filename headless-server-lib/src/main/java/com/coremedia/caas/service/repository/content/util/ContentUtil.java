package com.coremedia.caas.service.repository.content.util;

import com.coremedia.caas.service.repository.content.BlobProxy;
import com.coremedia.caas.service.repository.content.MarkupProxy;
import com.coremedia.cap.common.CapStructHelper;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.struct.Struct;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

public class ContentUtil {

  public static boolean isNullOrEmptyObject(Object value) {
    return (value == null) ||
           (value instanceof String && ((String) value).isEmpty()) ||
           (value instanceof Collection && ((Collection) value).isEmpty()) ||
           (value instanceof BlobProxy && ((BlobProxy) value).isEmpty()) ||
           (value instanceof MarkupProxy && ((MarkupProxy) value).isEmpty());
  }

  public static boolean isNullOrEmptyBlob(Object value) {
    return value == null || !(value instanceof BlobProxy) || ((BlobProxy) value).isEmpty();
  }

  public static boolean isNullOrEmptyLinklist(Object value) {
    return value == null || !(value instanceof Collection) || ((Collection) value).isEmpty();
  }

  public static boolean isNullOrEmptyRichtext(Object value) {
    return value == null || !(value instanceof MarkupProxy) || ((MarkupProxy) value).isEmpty();
  }


  public static ZonedDateTime getZonedDateTime(Content source, String propertyName) {
    return toZonedDateTime(source.getDate(propertyName));
  }

  public static ZonedDateTime getZonedDateTime(Struct source, String propertyName) {
    return toZonedDateTime(CapStructHelper.getDate(source, propertyName));
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
