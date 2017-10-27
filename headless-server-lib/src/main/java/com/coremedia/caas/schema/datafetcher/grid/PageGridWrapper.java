package com.coremedia.caas.schema.datafetcher.grid;

import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGrid;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PageGridWrapper {

  private ContentBackedPageGrid pageGrid;


  PageGridWrapper(ContentBackedPageGrid pageGrid) {
    this.pageGrid = pageGrid;
  }


  public String getCssClassName() {
    return pageGrid.getCssClassName();
  }

  public List<PageGridPlacementWrapper> getPlacements() {
    return pageGrid.getPlacements().entrySet().stream().map(e -> new PageGridPlacementWrapper(e.getKey(), e.getValue())).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
  }
}
