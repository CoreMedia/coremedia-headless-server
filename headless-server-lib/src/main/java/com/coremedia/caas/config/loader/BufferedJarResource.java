package com.coremedia.caas.config.loader;

import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;

class BufferedJarResource implements Resource {

  private String filename;
  private byte[] data;

  private JarConfigResourceLoader loader;


  BufferedJarResource(String filename, byte[] data, JarConfigResourceLoader loader) {
    this.filename = filename;
    this.data = data;
    this.loader = loader;
  }


  @Override
  public boolean exists() {
    return true;
  }

  @Override
  public boolean isReadable() {
    return true;
  }

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public URL getURL() throws IOException {
    throw new IOException("This is a virtual resource");
  }

  @Override
  public URI getURI() throws IOException {
    throw new IOException("This is a virtual resource");
  }

  @Override
  public File getFile() throws IOException {
    throw new IOException("This is a virtual resource");
  }

  @Override
  public long contentLength() {
    return data.length;
  }

  @Override
  public long lastModified() throws IOException {
    throw new IOException("This is a virtual resource");
  }

  @Override
  public Resource createRelative(String relativePath) {
    return loader.getResource(Paths.get(Paths.get(getFilename()).getParent().toString(), relativePath).toString());
  }

  @Override
  public String getFilename() {
    return filename;
  }

  @Override
  public String getDescription() {
    return "Virtual resource " + getFilename();
  }

  @Override
  public InputStream getInputStream() {
    return new ByteArrayInputStream(data);
  }
}
