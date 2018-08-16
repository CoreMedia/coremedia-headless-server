package com.coremedia.caas.server.service.security;

import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.security.AccessControlResultCode;
import com.coremedia.caas.service.security.AccessValidator;
import com.coremedia.cap.content.Content;

import java.time.ZonedDateTime;

import static com.coremedia.caas.schema.util.ContentUtil.getZonedDateTime;
import static com.coremedia.caas.service.request.ContextProperties.REQUEST_DATE;

public class ValidityDateValidator implements AccessValidator<Content> {

  @Override
  public AccessControlResultCode getErrorCode() {
    return AccessControlResultCode.INVALID_OBJECT;
  }


  @Override
  public boolean appliesTo(Object target) {
    return (target instanceof Content);
  }

  @Override
  public boolean validate(Content target, RootContext rootContext) {
    if (target.getType().isSubtypeOf("CMLinkable")) {
      ZonedDateTime now = rootContext.getRequestContext().getProperty(REQUEST_DATE, ZonedDateTime.class);
      if (now == null) {
        now = ZonedDateTime.now();
      }
      ZonedDateTime validFrom = getZonedDateTime(target, "validFrom");
      ZonedDateTime validTo = getZonedDateTime(target, "validTo");
      return (validFrom == null || !validFrom.isAfter(now)) && (validTo == null || validTo.isAfter(now));
    }
    return true;
  }

}
