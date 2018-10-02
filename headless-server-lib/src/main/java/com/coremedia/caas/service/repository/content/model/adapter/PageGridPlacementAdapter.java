package com.coremedia.caas.service.repository.content.model.adapter;

import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGridPlacement;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.caas.service.repository.content.ProxyObject;
import com.coremedia.caas.service.security.util.DateTimeRangeValidator;
import com.coremedia.cap.content.Content;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.coremedia.blueprint.base.pagegrid.PageGridContentKeywords.ANNOTATED_LINK_LIST_TARGET_PROPERTY_NAME;
import static com.coremedia.blueprint.base.pagegrid.PageGridContentKeywords.ANNOTATED_LINK_LIST_VISIBLE_FROM_PROPERTY_NAME;
import static com.coremedia.blueprint.base.pagegrid.PageGridContentKeywords.ANNOTATED_LINK_LIST_VISIBLE_TO_PROPERTY_NAME;

public class PageGridPlacementAdapter extends DateTimeRangeValidator implements ProxyObject {

  private String name;
  private ContentBackedPageGridPlacement pageGridPlacement;
  private RootContext rootContext;


  @SuppressWarnings("WeakerAccess")
  public PageGridPlacementAdapter(String name, ContentBackedPageGridPlacement pageGridPlacement, RootContext rootContext) {
    this.name = name;
    this.pageGridPlacement = pageGridPlacement;
    this.rootContext = rootContext;
  }


  public String getName() {
    return name;
  }

  @SuppressWarnings("unused")
  public String getViewtype() {
    Content viewtype = pageGridPlacement.getViewtype();
    return viewtype != null ? viewtype.getString("layout") : "default";
  }

  @SuppressWarnings("unused")
  public List<ContentProxy> getItems() {
    return rootContext.getProxyFactory().makeStructProxyList(pageGridPlacement.getExtendedItems()).stream()
            .map(e -> {
              ContentProxy link = e.getLink(ANNOTATED_LINK_LIST_TARGET_PROPERTY_NAME);
              return (link != null && validate(e.getDate(ANNOTATED_LINK_LIST_VISIBLE_FROM_PROPERTY_NAME),
                                               e.getDate(ANNOTATED_LINK_LIST_VISIBLE_TO_PROPERTY_NAME),
                                               rootContext))
                     ? link
                     : null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
  }
}
