package com.coremedia.caas.service.repository.content;

import com.coremedia.dtd.RichtextDtd;
import com.coremedia.xml.Markup;
import com.coremedia.xml.MarkupUtil;

import java.util.Objects;

public class MarkupProxyImpl implements MarkupProxy {

  private Markup delegate;


  public MarkupProxyImpl(Markup delegate) {
    this.delegate = delegate;
  }


  @Override
  public boolean isEmpty() {
    return !RichtextDtd.GRAMMAR_NAME.equals(delegate.getGrammar()) || MarkupUtil.isEmptyRichtext(delegate, true);
  }

  @Override
  public long weight() {
    return delegate.weight();
  }

  @Override
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
    MarkupProxyImpl that = (MarkupProxyImpl) o;
    return Objects.equals(delegate, that.delegate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(delegate);
  }
}
