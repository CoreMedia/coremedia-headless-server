package com.coremedia.caas.service.repository.content;

import com.coremedia.cap.common.Blob;

import com.google.common.base.Objects;

import javax.activation.MimeType;

public class BlobProxy {

  private Blob delegate;


  public BlobProxy(Blob delegate) {
    this.delegate = delegate;
  }


  public MimeType getContentType() {
    return delegate.getContentType();
  }

  public int getSize() {
    return delegate.getSize();
  }

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
    BlobProxy blobProxy = (BlobProxy) o;
    return Objects.equal(delegate, blobProxy.delegate);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(delegate);
  }
}
