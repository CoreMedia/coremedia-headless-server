package com.coremedia.caas.config;

import com.coremedia.caas.config.loader.ConfigResourceLoader;
import com.coremedia.caas.schema.InvalidDefinition;
import com.coremedia.cap.content.ContentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class ProcessingDefinitionLoader {

  private static final Logger LOG = LoggerFactory.getLogger(ProcessingDefinitionLoader.class);


  private String name;
  private ConfigResourceLoader resourceLoader;
  private ContentRepository contentRepository;
  private ApplicationContext applicationContext;


  public ProcessingDefinitionLoader(String name, ConfigResourceLoader resourceLoader, ContentRepository contentRepository, ApplicationContext applicationContext) {
    this.name = name;
    this.resourceLoader = resourceLoader;
    this.contentRepository = contentRepository;
    this.applicationContext = applicationContext;
  }


  public ProcessingDefinition load() throws InvalidDefinition, IOException {
    ProcessingDefinition definition = new ProcessingDefinitionReader(name, applicationContext, contentRepository, resourceLoader).read();
    LOG.info("Processing definition loaded: {}", definition);
    return definition;
  }
}
