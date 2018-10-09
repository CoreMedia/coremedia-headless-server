package com.coremedia.caas.server.service.security;

import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.security.AccessControlResultCode;
import com.coremedia.caas.service.security.AccessValidator;
import com.coremedia.caas.service.security.util.DateTimeRangeValidator;
import com.coremedia.cap.content.Content;

import java.time.ZonedDateTime;

import static com.coremedia.caas.service.repository.content.util.ContentUtil.getZonedDateTime;

public class ValidityDateValidator extends DateTimeRangeValidator implements AccessValidator<Content> {

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
      ZonedDateTime validFrom = getZonedDateTime(target, "validFrom");
      ZonedDateTime validTo = getZonedDateTime(target, "validTo");
      return validate(validFrom, validTo, rootContext);
    }
    return true;
  }
}
