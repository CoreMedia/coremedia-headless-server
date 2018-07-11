package com.coremedia.caas.service.repository.content.model.adapter;

import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGridPlacement;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.cap.content.Content;

import java.util.List;

public class PageGridPlacementAdapter {

  private String name;
  private ContentBackedPageGridPlacement pageGridPlacement;
  private RootContext rootContext;


  public PageGridPlacementAdapter(String name, ContentBackedPageGridPlacement pageGridPlacement, RootContext rootContext) {
    this.name = name;
    this.pageGridPlacement = pageGridPlacement;
    this.rootContext = rootContext;
  }


  public String getName() {
    return name;
  }

  public String getViewtype() {
    Content viewtype = pageGridPlacement.getViewtype();
    return viewtype != null ? viewtype.getString("layout") : "default";
  }

  public List<ContentProxy> getItems() {
    return rootContext.getProxyFactory().makeContentProxyList(pageGridPlacement.getItems());
  }
}
