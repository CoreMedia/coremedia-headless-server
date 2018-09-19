package com.coremedia.caas.link;

import com.coremedia.caas.service.repository.RootContext;

public interface LinkBuilder<E> {

  E createLink(Object target, RootContext rootContext);
}
