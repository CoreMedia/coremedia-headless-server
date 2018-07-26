package com.coremedia.caas.richtext.stax;

import com.coremedia.caas.service.repository.content.MarkupProxy;

import com.google.common.base.Objects;

public class MarkupCacheKey {

  private MarkupProxy markupProxy;
  private StaxRichtextTransformer transformer;


  public MarkupCacheKey(MarkupProxy markupProxy, StaxRichtextTransformer transformer) {
    this.markupProxy = markupProxy;
    this.transformer = transformer;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MarkupCacheKey cacheKey = (MarkupCacheKey) o;
    return Objects.equal(markupProxy, cacheKey.markupProxy) &&
           Objects.equal(transformer, cacheKey.transformer);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(markupProxy, transformer);
  }
}
