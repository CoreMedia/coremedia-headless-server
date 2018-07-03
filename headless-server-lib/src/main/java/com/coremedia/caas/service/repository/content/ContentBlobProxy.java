package com.coremedia.caas.service.repository.content;

import com.coremedia.cap.common.Blob;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.MimeType;

class ContentBlobProxy implements Blob {

  private Blob delegate;
  private ContentProxy content;


  ContentBlobProxy(Blob delegate, ContentProxy content) {
    this.delegate = delegate;
    this.content = content;
  }


  public ContentProxy getContent() {
    return content;
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
