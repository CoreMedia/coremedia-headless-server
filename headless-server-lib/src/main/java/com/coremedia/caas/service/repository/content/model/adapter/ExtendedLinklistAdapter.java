package com.coremedia.caas.service.repository.content.model.adapter;

import com.coremedia.caas.service.repository.ProxyFactory;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.cap.common.CapStructHelper;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.struct.Struct;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExtendedLinklistAdapter {

  private Content content;
  private RootContext rootContext;
  private String propertyName;
  private String propertyPath;
  private String fallbackPropertyName;
  private String targetTypeName;
  private String targetPropertyName;


  public ExtendedLinklistAdapter(Content content, RootContext rootContext, String propertyName, String propertyPath, String fallbackPropertyName, String targetTypeName, String targetPropertyName) {
    this.content = content;
    this.rootContext = rootContext;
    this.propertyName = propertyName;
    this.propertyPath = propertyPath;
    this.fallbackPropertyName = fallbackPropertyName;
    this.targetTypeName = targetTypeName;
    this.targetPropertyName = targetPropertyName;
  }


  private Stream<ContentProxy> getTargetStream() {
    ProxyFactory proxyFactory = rootContext.getProxyFactory();
    // check struct of extended link items first
    Struct extendedLinklist = content.getStruct(propertyName);
    if (extendedLinklist != null) {
      List<Struct> extendedItems = CapStructHelper.getStructs(extendedLinklist, propertyPath);
      if (extendedItems != null) {
        return extendedItems.stream()
                .map(e -> CapStructHelper.getLink(e, targetPropertyName))
                .filter(Objects::nonNull)
                .filter(e -> e.getType().isSubtypeOf(targetTypeName))
                .map(proxyFactory::makeContentProxy)
                .filter(Objects::nonNull);
      }
    }
    // use legacy property as fallback
    if (fallbackPropertyName != null) {
      return content.getLinks(fallbackPropertyName).stream()
              .filter(e -> e.getType().isSubtypeOf(targetTypeName))
              .map(proxyFactory::makeContentProxy)
              .filter(Objects::nonNull);
    }
    return null;
  }


  public ContentProxy getTarget() {
    // fetch resolved target stream and return first item
    Stream<ContentProxy> stream = getTargetStream();
    if (stream != null) {
      return stream.findFirst().orElse(null);
    }
    return null;
  }

  public List<ContentProxy> getTargets() {
    // fetch resolved target stream and return all items
    Stream<ContentProxy> stream = getTargetStream();
    if (stream != null) {
      return stream.collect(Collectors.toList());
    }
    return Collections.emptyList();
  }
}
