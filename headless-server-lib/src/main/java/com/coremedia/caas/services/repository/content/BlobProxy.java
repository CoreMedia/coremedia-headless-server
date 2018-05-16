package com.coremedia.caas.services.repository.content;

import com.coremedia.cap.common.Blob;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.MimeType;

class BlobProxy implements Blob {

  private Blob delegate;


  BlobProxy(Blob delegate) {
    this.delegate = delegate;
  }


  @Override
  public MimeType getContentType() {
    return delegate.getContentType();
  }

  @Override
  public int getSize() {
    return delegate.getSize();
  }

  @Override
  public String getETag() {
    return delegate.getETag();
  }

  @Override
  public void writeOn(OutputStream outputStream) throws IOException {
    delegate.writeOn(outputStream);
  }

  @Override
  public InputStream getInputStream() {
    return delegate.getInputStream();
  }

  @Override
  public byte[] asBytes() {
    return delegate.asBytes();
  }

  @Override
  public void dispose() {
    delegate.dispose();
  }
}
