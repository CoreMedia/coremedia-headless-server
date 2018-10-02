package com.coremedia.caas.service.security.util;

import com.coremedia.caas.service.repository.RootContext;

import java.time.ZonedDateTime;

public abstract class DateTimeRangeValidator {

  protected boolean validate(ZonedDateTime validFrom, ZonedDateTime validTo, RootContext rootContext) {
    ZonedDateTime now = rootContext.getRequestContext().getRequestTime();
    // update date and time of next content change
    ZonedDateTime nextChange = null;
    if (validFrom != null && validFrom.isAfter(now)) {
      nextChange = validFrom;
    }
    if (validTo != null && validTo.isAfter(now) && (validFrom == null || validTo.isBefore(validFrom))) {
      nextChange = validTo;
    }
    if (nextChange != null) {
      rootContext.getRequestContext().updateNextDateTimeChange(nextChange);
    }
    return (validFrom == null || !validFrom.isAfter(now)) && (validTo == null || validTo.isAfter(now));
  }
}
