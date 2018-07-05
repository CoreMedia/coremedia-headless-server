package com.coremedia.caas.config.reader;

import com.coremedia.caas.config.loader.ConfigResourceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AbstractConfigReader implements ConfigReader {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractConfigReader.class);


  private ConfigResourceLoader resourceLoader;


  protected AbstractConfigReader(ConfigResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }


  public ConfigResourceLoader getResourceLoader() {
    return resourceLoader;
  }


  @Override
  public ConfigResource getResource(String pattern) {
    Resource resource = resourceLoader.getResource(pattern);
    if (resource.exists()) {
      return new ConfigResource(resource);
    }
    return null;
  }

  @Override
  public List<ConfigResource> getResources(String pattern) {
    try {
      return Arrays.stream(resourceLoader.getResources(pattern)).map(ConfigResource::new).collect(Collectors.toList());
    } catch (IOException e) {
      LOG.error("Error reading resources: {}", e);
      return Collections.emptyList();
    }
  }
}
