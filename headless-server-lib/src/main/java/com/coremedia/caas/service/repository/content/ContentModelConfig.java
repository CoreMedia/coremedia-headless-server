package com.coremedia.caas.service.repository.content;

import com.coremedia.blueprint.base.navigation.context.ContextStrategy;
import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGridService;
import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.blueprint.base.tree.TreeRelation;
import com.coremedia.caas.service.repository.content.model.ContentExtendedLinklistModelFactory;
import com.coremedia.caas.service.repository.content.model.ContentModelFactory;
import com.coremedia.caas.service.repository.content.model.ContentNavigationModelFactory;
import com.coremedia.caas.service.repository.content.model.ContentPageGridModelFactory;
import com.coremedia.caas.service.repository.content.model.ContentSettingsModelFactory;
import com.coremedia.cap.content.Content;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class ContentModelConfig {

  @Bean
  public ContentProxyModelFactory contentModelAdapterFactory(List<ContentModelFactory> modelFactories) {
    return new ContentProxyModelFactory(modelFactories);
  }


  @Bean
  public ContentExtendedLinklistModelFactory contentExtendedLinklistModelFactory() {
    return new ContentExtendedLinklistModelFactory();
  }

  @Bean
  public ContentNavigationModelFactory contentNavigationModelFactory(@Qualifier("contentContextStrategy") ContextStrategy<Content, Content> contextStrategy, Map<String, TreeRelation<Content>> treeRelations) {
    return new ContentNavigationModelFactory(contextStrategy, treeRelations);
  }

  @Bean
  public ContentPageGridModelFactory contentPageGridModelFactory(@Qualifier("contentBackedPageGridService") ContentBackedPageGridService contentBackedPageGridService) {
    return new ContentPageGridModelFactory(contentBackedPageGridService);
  }

  @Bean
  public ContentSettingsModelFactory contentSettingsModelFactory(@Qualifier("settingsService") SettingsService settingsService) {
    return new ContentSettingsModelFactory(settingsService);
  }


  @Bean("queryContentModelMethodResolver")
  public ContentProxyModelResolver queryContentModelMethodResolver(ContentProxyModelFactory contentProxyModelFactory) {
    return new ContentProxyModelResolver(contentProxyModelFactory, true);
  }

  @Bean("schemaContentModelMethodResolver")
  public ContentProxyModelResolver schemaContentModelMethodResolver(ContentProxyModelFactory contentProxyModelFactory) {
    return new ContentProxyModelResolver(contentProxyModelFactory, false);
  }
}
