package com.coremedia.caas.schema.datafetcher.grid;

import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGrid;
import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGridService;
import com.coremedia.caas.schema.datafetcher.property.AbstractPropertyDataFetcher;
import com.coremedia.cap.content.Content;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageGridDataFetcher extends AbstractPropertyDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(PageGridDataFetcher.class);


  public PageGridDataFetcher(String sourceName) {
    super(sourceName);
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    Object source = environment.getSource();
    try {
      if (!(source instanceof Content)) {
        throw new IllegalArgumentException("Not an UAPI content: " + source);
      }
      Content content = (Content) source;
      ContentBackedPageGridService contentBackedPageGridService = getContext(environment).getServiceRegistry().getContentBackedPageGridService();
      ContentBackedPageGrid pageGrid = contentBackedPageGridService.getContentBackedPageGrid(content, sourceName);
      return new PageGridWrapper(pageGrid);
    } catch (Exception e) {
      LOG.error("DataFetcher access failed:", e);
    }
    return null;
  }
}
