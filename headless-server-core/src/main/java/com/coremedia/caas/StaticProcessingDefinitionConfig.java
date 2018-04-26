package com.coremedia.caas;

import com.coremedia.caas.config.CaasProcessingDefinition;
import com.coremedia.caas.config.CaasProcessingDefinitionLoader;
import com.coremedia.caas.config.loader.ClasspathConfigResourceLoader;
import com.coremedia.caas.schema.InvalidDefinition;
import com.coremedia.cap.content.ContentRepository;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;

@Configuration
public class StaticProcessingDefinitionConfig {

  @Autowired
  @Qualifier("minimalProcessingDefinition")
  private CaasProcessingDefinition minimalProcessingDefinition;


  @Bean("staticProcessingDefinitions")
  public Map<String, CaasProcessingDefinition> loadStaticDefinitions(ContentRepository contentRepository, ApplicationContext applicationContext) throws InvalidDefinition, IOException {
    ClasspathConfigResourceLoader sampleResourceLoader = new ClasspathConfigResourceLoader("pd/sample/");
    return ImmutableMap.of(
            "default", new CaasProcessingDefinitionLoader("default", sampleResourceLoader, contentRepository, applicationContext).load(),
            "fallback", minimalProcessingDefinition
    );
  }
}
