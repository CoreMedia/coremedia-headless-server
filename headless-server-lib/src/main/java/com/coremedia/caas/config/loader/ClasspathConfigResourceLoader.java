package com.coremedia.caas.config.loader;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

public class ClasspathConfigResourceLoader implements ConfigResourceLoader {

  private String basePath;
  private PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();


  public ClasspathConfigResourceLoader(String basePath) {
    this.basePath = basePath;
  }


  public PathMatchingResourcePatternResolver getLoader() {
    return loader;
  }

  public void setLoader(PathMatchingResourcePatternResolver loader) {
    this.loader = loader;
  }


  @Override
  public Resource getResource(String pattern) {
    return getLoader().getResource("classpath:" + basePath + pattern);
  }

  @Override
  public Resource[] getResources(String pattern) throws IOException {
    return getLoader().getResources("classpath:" + basePath + pattern);
  }
}
