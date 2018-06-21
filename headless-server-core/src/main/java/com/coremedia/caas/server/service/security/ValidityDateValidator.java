package com.coremedia.caas.server.service.security;

import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.security.AccessControlResultCode;
import com.coremedia.caas.service.security.AccessValidator;
import com.coremedia.cap.content.Content;

import java.util.Calendar;

import static com.coremedia.caas.server.service.request.ContextProperties.REQUEST_DATE;

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
      Calendar now = rootContext.getRequestContext().getProperty(REQUEST_DATE, Calendar.class);
      Calendar validFrom = target.getDate("validFrom");
      Calendar validTo = target.getDate("validTo");
      return (validFrom == null || validFrom.compareTo(now) <= 0) && (validTo == null || validTo.compareTo(now) > 0);
    }
    return true;
  }
}
