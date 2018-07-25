package com.coremedia.caas.service.repository.content;

import com.coremedia.xml.Markup;

import com.google.common.base.Objects;

public class MarkupProxy {

  private Markup delegate;


  public MarkupProxy(Markup delegate) {
    this.delegate = delegate;
  }


  public long weight() {
    return delegate.weight();
  }


  public String asXml() {
    return delegate.asXml();
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MarkupProxy that = (MarkupProxy) o;
    return Objects.equal(delegate, that.delegate);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(delegate);
  }
}