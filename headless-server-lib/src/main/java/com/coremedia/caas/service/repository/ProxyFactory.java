package com.coremedia.caas.service.repository;

import com.coremedia.caas.service.repository.content.BlobProxy;
import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.caas.service.repository.content.MarkupProxy;
import com.coremedia.caas.service.repository.content.StructProxy;
import com.coremedia.cap.common.Blob;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.struct.Struct;
import com.coremedia.xml.Markup;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

public interface ProxyFactory {

  RootContext getRootContext();


  Object makeRoot(Object source);


  Object makeProxy(Object source);


  <T> Map<T, ?> makeProxyMap(@NotNull Map<T, ?> source);


  BlobProxy makeBlobProxy(@NotNull Blob source);

  MarkupProxy makeMarkupProxy(@NotNull Markup source);

  StructProxy makeStructProxy(@NotNull Struct source);


  ContentProxy makeContentProxy(@NotNull Content source);

  ContentProxy makeContentProxyFromId(@NotNull String id);

  List<ContentProxy> makeContentProxyList(@NotNull Collection<Content> source);

  List<ContentProxy> makeContentProxyList(@NotNull Collection<Content> source, int limit);

  List<ContentProxy> makeContentProxyListFromIds(@NotNull Collection<String> ids);

  List<ContentProxy> makeContentProxyListFromIds(@NotNull Collection<String> ids, int limit);
}
