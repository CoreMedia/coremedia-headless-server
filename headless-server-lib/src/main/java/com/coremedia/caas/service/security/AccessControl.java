package com.coremedia.caas.service.security;

public interface AccessControl {

  boolean check(Object target);

  void checkAndThrow(Object target) throws AccessControlViolation;
}
