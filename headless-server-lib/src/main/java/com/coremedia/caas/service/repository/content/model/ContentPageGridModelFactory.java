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
  public boolean isQueryModel() {
    return false;
  }

  @Override
  public String getModelName() {
    return PAGEGRID_MODEL;
  }

  @Override
  public PageGridAdapter createModel(RootContext rootContext, Content content, Object... arguments) {
    // resolve arguments
    String sourceProperyName = (String) arguments[0];
    // instantiate model adapter
    ContentBackedPageGrid pageGrid = contentBackedPageGridService.getContentBackedPageGrid(content, sourceProperyName);
    return new PageGridAdapter(pageGrid, rootContext);
  }
}
