package com.coremedia.caas.services.repository;

import com.coremedia.caas.services.repository.content.ContentProxy;
import com.coremedia.cap.content.Content;

import java.util.Collection;
import java.util.List;

public interface ProxyFactory {

  RootContext getRootContext();


  Object makeProxy(Object source);


  ContentProxy makeContentProxy(Content source);

  List<ContentProxy> makeContentProxyList(Collection<Content> source);
}
