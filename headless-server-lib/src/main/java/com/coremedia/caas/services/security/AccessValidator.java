package com.coremedia.caas.services.security;

import com.coremedia.caas.services.repository.RootContext;

public interface AccessValidator<E> {

  boolean appliesTo(Object target);

  boolean validate(E target, RootContext rootContext);


  AccessControlResultCode getErrorCode();
}
