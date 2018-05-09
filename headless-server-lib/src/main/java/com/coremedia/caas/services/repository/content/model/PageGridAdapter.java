package com.coremedia.caas.services.repository.content.model;

import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGrid;
import com.coremedia.caas.services.repository.RootContext;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PageGridAdapter {

  private ContentBackedPageGrid pageGrid;
  private RootContext rootContext;


  public PageGridAdapter(ContentBackedPageGrid pageGrid, RootContext rootContext) {
    this.pageGrid = pageGrid;
    this.rootContext = rootContext;
  }


  public String getCssClassName() {
    return pageGrid.getCssClassName();
  }

  public List<PageGridPlacementAdapter> getPlacements() {
    return pageGrid.getPlacements().entrySet().stream().map(e -> new PageGridPlacementAdapter(e.getKey(), e.getValue(), rootContext)).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
  }
}
