package com.coremedia.caas.config;

import com.coremedia.caas.config.loader.ClasspathConfigResourceLoader;
import com.coremedia.caas.schema.InvalidDefinition;
import com.coremedia.cap.content.ContentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class BaseProcessingDefinitionConfig {

  protected static final Logger LOG = LoggerFactory.getLogger(BaseProcessingDefinitionConfig.class);


  @Autowired
  protected ContentRepository contentRepository;


  @Bean("minimalProcessingDefinition")
  public CaasProcessingDefinition createMinimalDefinition(ApplicationContext applicationContext) throws InvalidDefinition, IOException {
    LOG.info("creating minimal config...");
    return new CaasProcessingDefinitionLoader("minimal", new ClasspathConfigResourceLoader("pd/minimal/"), contentRepository, applicationContext).load();
  }
}
