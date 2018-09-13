package com.coremedia.caas.service.repository.content.model.adapter;

import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGrid;
import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGridPlacement;
import com.coremedia.blueprint.base.pagegrid.ContentBackedStyle;
import com.coremedia.blueprint.base.pagegrid.ContentBackedStyleGrid;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.cap.content.Content;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PageGridAdapter {

  private ContentBackedPageGrid pageGrid;
  private RootContext rootContext;


  public PageGridAdapter(ContentBackedPageGrid pageGrid, RootContext rootContext) {
    this.pageGrid = pageGrid;
    this.rootContext = rootContext;
  }


  @SuppressWarnings("unused")
  public String getCssClassName() {
    return pageGrid.getCssClassName();
  }

  @SuppressWarnings("unused")
  public List<PageGridPlacementAdapter> getPlacements() {
    Map<String, ContentBackedPageGridPlacement> placements = pageGrid.getPlacements();
    ContentBackedStyleGrid styleGrid = pageGrid.getStyleGrid();
    // fetch placements by style row order
    return IntStream.range(0, styleGrid.getNumRows())
            .mapToObj(styleGrid::getRow)
            .flatMap(Collection::stream)
            .map(ContentBackedStyle::getSection)
            .map(Content::getName)
            .map(e -> new PageGridPlacementAdapter(e, placements.get(e), rootContext))
            .collect(Collectors.toList());
  }
}
