package com.coremedia.caas;

import com.coremedia.caas.config.CaasProcessingDefinition;
import com.coremedia.caas.config.CaasProcessingDefinitionLoader;
import com.coremedia.caas.config.loader.ClasspathConfigResourceLoader;
import com.coremedia.caas.schema.InvalidDefinition;
import com.coremedia.cap.content.ContentRepository;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Map;

@Configuration
public class StaticProcessingDefinitionConfig {

  private static final Logger LOG = LoggerFactory.getLogger(StaticProcessingDefinitionConfig.class);

  private static final String PD_PATH = "pd/static";
  private static final String PD_DEFINITION = "definition.yml";


  @Bean("staticProcessingDefinitions")
  public Map<String, CaasProcessingDefinition> loadStaticDefinitions(ContentRepository contentRepository, ApplicationContext applicationContext) throws InvalidDefinition, IOException {
    ImmutableMap.Builder<String, CaasProcessingDefinition> builder = ImmutableMap.builder();
    // find all deployed definitions
    PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
    for (Resource resource : loader.getResources("classpath:" + PD_PATH + "/**/" + PD_DEFINITION)) {
      if (resource instanceof ClassPathResource) {
        ClassPathResource classPathResource = (ClassPathResource) resource;
        // extract classpath path and definition name
        String resourcePath = classPathResource.getPath();
        String resourceName = classPathResource.getFilename();
        String definitionPath = resourcePath.substring(0, resourcePath.length() - resourceName.length());
        String definitionName = definitionPath.substring(PD_PATH.length() + 1, definitionPath.length() - 1);
        LOG.info("Registering processing definition '{}'", definitionName);
        CaasProcessingDefinition definition = new CaasProcessingDefinitionLoader(definitionName, new ClasspathConfigResourceLoader(definitionPath), contentRepository, applicationContext).load();
        builder.put(definitionName, definition);
      }
    }
    return builder.build();
  }
}
