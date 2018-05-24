package com.coremedia.caas.service.security;

import com.coremedia.caas.services.repository.RootContext;
import com.coremedia.caas.services.security.AccessControlResultCode;
import com.coremedia.caas.services.security.AccessValidator;
import com.coremedia.cap.content.Content;

import java.util.Calendar;
import java.util.Date;

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
      Date now = rootContext.getRequestContext().getProperty(REQUEST_DATE, Date.class);
      Calendar validFrom = target.getDate("validFrom");
      Calendar validTo = target.getDate("validTo");
      return (validFrom == null || !now.before(validFrom.getTime())) && (validTo == null || !now.after(validTo.getTime()));
    }
    return true;
  }
}
