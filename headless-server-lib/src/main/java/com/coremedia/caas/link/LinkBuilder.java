package com.coremedia.caas.link;

import com.coremedia.caas.service.repository.RootContext;

public interface LinkBuilder {

  String createLink(Object target, RootContext rootContext);
}
