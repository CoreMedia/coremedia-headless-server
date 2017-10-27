package com.coremedia.caas.schema.datafetcher.grid;

import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGridPlacement;
import com.coremedia.cap.content.Content;

import java.util.List;

public class PageGridPlacementWrapper {

  private String name;
  private ContentBackedPageGridPlacement pageGridPlacement;


  PageGridPlacementWrapper(String name, ContentBackedPageGridPlacement pageGridPlacement) {
    this.name = name;
    this.pageGridPlacement = pageGridPlacement;
  }


  public String getName() {
    return name;
  }

  public Content getViewtype() {
    return pageGridPlacement.getViewtype();
  }

  public List<Content> getItems() {
    return pageGridPlacement.getItems();
  }
}
