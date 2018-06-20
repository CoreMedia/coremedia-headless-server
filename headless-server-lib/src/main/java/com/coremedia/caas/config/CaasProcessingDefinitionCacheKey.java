package com.coremedia.caas.config;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.config.loader.JarConfigResourceLoader;
import com.coremedia.caas.schema.InvalidDefinition;
import com.coremedia.cache.Cache;
import com.coremedia.cache.CacheKey;
import com.coremedia.cap.common.Blob;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.jar.JarInputStream;

public class CaasProcessingDefinitionCacheKey extends CacheKey<Map<String, CaasProcessingDefinition>> {

  private static final Logger LOG = LoggerFactory.getLogger(CaasProcessingDefinitionCacheKey.class);

  private static final String KEY_DEFINITIONS = "caasDefinitions";

  private static final String PROPERTY_DEFINITION_DATA = "data";


  private String siteId;
  private ContentRepository contentRepository;
  private SettingsService settingsService;
  private ApplicationContext applicationContext;


  public CaasProcessingDefinitionCacheKey(Content siteIndicator, SettingsService settingsService, ApplicationContext applicationContext) {
    this.siteId = siteIndicator.getId();
    this.contentRepository = siteIndicator.getRepository();
    this.settingsService = settingsService;
    this.applicationContext = applicationContext;
  }


  @Override
  public Map<String, CaasProcessingDefinition> evaluate(Cache cache) {
    Content site = contentRepository.getContent(siteId);
    if (site != null) {
      Map<String, Content> caasDefinitions = settingsService.settingAsMap(KEY_DEFINITIONS, String.class, Content.class, site);
      ImmutableMap.Builder<String, CaasProcessingDefinition> builder = ImmutableMap.builder();
      for (Map.Entry<String, Content> entry : caasDefinitions.entrySet()) {
        String name = entry.getKey();
        Content content = entry.getValue();
        if (content != null && content.getProperties().containsKey(PROPERTY_DEFINITION_DATA)) {
          Blob data = content.getBlobRef(PROPERTY_DEFINITION_DATA);
          if (data != null) {
            try (InputStream inputStream = data.getInputStream(); JarInputStream jarInputStream = new JarInputStream(inputStream)) {
              JarConfigResourceLoader resourceLoader = new JarConfigResourceLoader(jarInputStream);
              builder.put(name, new CaasProcessingDefinitionLoader(name, resourceLoader, contentRepository, applicationContext).load());
            } catch (InvalidDefinition | IOException e) {
              LOG.error("Cannot load definition '{}'", name, e);
            }
          }
        }
      }
      return builder.build();
    }
    return ImmutableMap.of();
  }


  @Override
  public int hashCode() {
    return Objects.hashCode(siteId, contentRepository);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CaasProcessingDefinitionCacheKey definitionCacheKey = (CaasProcessingDefinitionCacheKey) o;
    return Objects.equal(siteId, definitionCacheKey.siteId) &&
           Objects.equal(contentRepository, definitionCacheKey.contentRepository);
  }
}
