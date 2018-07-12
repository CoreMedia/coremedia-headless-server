package com.coremedia.caas.service.repository;

import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.cap.content.Content;

import java.util.Collection;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface ProxyFactory {

  RootContext getRootContext();


  Object makeRoot(Object source);


  Object makeProxy(Object source);


  ContentProxy makeContentProxy(@NotNull Content source);

  ContentProxy makeContentProxyFromId(@NotNull String id);

  List<ContentProxy> makeContentProxyList(@NotNull Collection<Content> source);

  List<ContentProxy> makeContentProxyList(@NotNull Collection<Content> source, int limit);

  List<ContentProxy> makeContentProxyListFromIds(@NotNull Collection<String> ids);

  List<ContentProxy> makeContentProxyListFromIds(@NotNull Collection<String> ids, int limit);
}
