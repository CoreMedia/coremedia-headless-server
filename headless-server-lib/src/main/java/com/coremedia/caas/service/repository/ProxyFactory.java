package com.coremedia.caas.service.repository;

import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.cap.content.Content;

import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;

public interface ProxyFactory {

  RootContext getRootContext();


  Object makeRoot(Object source);


  Object makeProxy(Object source);


  ContentProxy makeContentProxy(@Nonnull Content source);

  ContentProxy makeContentProxy(@Nonnull String id);

  List<ContentProxy> makeContentProxyList(@Nonnull Collection<Content> source);
}
