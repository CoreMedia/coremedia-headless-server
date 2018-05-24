package com.coremedia.caas.services.security;

public interface AccessControl {

  boolean check(Object target);

  void checkAndThrow(Object target) throws AccessControlViolation;
}
