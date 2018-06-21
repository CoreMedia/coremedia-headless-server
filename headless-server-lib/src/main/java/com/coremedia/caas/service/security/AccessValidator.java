package com.coremedia.caas.service.security;

import com.coremedia.caas.service.repository.RootContext;

public interface AccessValidator<E> {

  boolean appliesTo(Object target);

  boolean validate(E target, RootContext rootContext);


  AccessControlResultCode getErrorCode();
}
