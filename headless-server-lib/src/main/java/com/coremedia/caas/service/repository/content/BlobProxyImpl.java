package com.coremedia.caas.service.repository.content;

import com.coremedia.cap.common.Blob;

import java.util.Objects;
import javax.activation.MimeType;

public class BlobProxyImpl implements BlobProxy {

  private Blob delegate;


  public BlobProxyImpl(Blob delegate) {
    this.delegate = delegate;
  }


  @Override
  public boolean isEmpty() {
    return getSize() == 0;
  }

  @Override
  public int getSize() {
    return delegate.getSize();
  }

  @Override
  public MimeType getContentType() {
    return delegate.getContentType();
  }

  @Override
  public String getETag() {
    return delegate.getETag();
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BlobProxyImpl blobProxy = (BlobProxyImpl) o;
    return Objects.equals(delegate, blobProxy.delegate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(delegate);
  }
}
