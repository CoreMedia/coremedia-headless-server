package com.coremedia.caas.service.repository.content.model;

import com.coremedia.caas.schema.datafetcher.DataFetcherException;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.repository.content.model.adapter.ExtendedLinklistAdapter;
import com.coremedia.cap.content.Content;

import static com.coremedia.caas.service.repository.ModelFactory.EXTENDEDLINKLIST_MODEL;

public class ContentExtendedLinklistModelFactory implements ContentModelFactory<ExtendedLinklistAdapter> {

  @Override
  public boolean isQueryModel() {
    return false;
  }

  @Override
  public String getModelName() {
    return EXTENDEDLINKLIST_MODEL;
  }

  @Override
  public ExtendedLinklistAdapter createModel(RootContext rootContext, Content content, Object... arguments) {
    if (arguments.length < 2 || arguments.length > 3) {
      throw new DataFetcherException("Invalid number of arguments", null);
    }
    String propertyName = (String) arguments[0];
    String propertyPath = "links";
    String fallbackPropertyName = (String) arguments[1];
    String targetTypeName = arguments.length == 3 ? (String) arguments[2] : "CMLinkable";
    String targetPropertyName = "target";
    return new ExtendedLinklistAdapter(content, rootContext, propertyName, propertyPath, fallbackPropertyName, targetTypeName, targetPropertyName);
  }
}
