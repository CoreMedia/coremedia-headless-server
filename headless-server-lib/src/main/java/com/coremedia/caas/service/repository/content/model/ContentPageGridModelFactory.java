package com.coremedia.caas.service.repository.content.model;

import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGrid;
import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGridService;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.repository.content.model.adapter.PageGridAdapter;
import com.coremedia.cap.content.Content;

import static com.coremedia.caas.service.repository.ModelFactory.PAGEGRID_MODEL;

public class ContentPageGridModelFactory implements ContentModelFactory<PageGridAdapter> {

  private ContentBackedPageGridService contentBackedPageGridService;


  public ContentPageGridModelFactory(ContentBackedPageGridService contentBackedPageGridService) {
    this.contentBackedPageGridService = contentBackedPageGridService;
  }


  @Override
  public boolean isExpressionModel() {
    return false;
  }

  @Override
  public String getModelName() {
    return PAGEGRID_MODEL;
  }

  @Override
  public PageGridAdapter createModel(Content content, String propertyPath, RootContext rootContext) {
    ContentBackedPageGrid pageGrid = contentBackedPageGridService.getContentBackedPageGrid(content, propertyPath);
    return new PageGridAdapter(pageGrid, rootContext);
  }
}
