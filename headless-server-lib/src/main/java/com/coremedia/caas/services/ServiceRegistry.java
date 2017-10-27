package com.coremedia.caas.services;

import com.coremedia.blueprint.base.navigation.context.ContextStrategy;
import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGridService;
import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.blueprint.base.tree.TreeRelation;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import org.springframework.core.convert.ConversionService;

public class ServiceRegistry {

  private ContentRepository contentRepository;
  private SettingsService settingsService;
  private ContentBackedPageGridService contentBackedPageGridService;
  private ContextStrategy<Content, Content> contextStrategy;
  private TreeRelation<Content> treeRelation;
  private ConversionService conversionService;


  public ServiceRegistry(ContentRepository contentRepository, SettingsService settingsService, ContentBackedPageGridService contentBackedPageGridService, ContextStrategy<Content, Content> contextStrategy, TreeRelation<Content> treeRelation, ConversionService conversionService) {
    this.contentRepository = contentRepository;
    this.settingsService = settingsService;
    this.contentBackedPageGridService = contentBackedPageGridService;
    this.contextStrategy = contextStrategy;
    this.treeRelation = treeRelation;
    this.conversionService = conversionService;
  }


  public ContentRepository getContentRepository() {
    return contentRepository;
  }

  public SettingsService getSettingsService() {
    return settingsService;
  }

  public ContentBackedPageGridService getContentBackedPageGridService() {
    return contentBackedPageGridService;
  }

  public ContextStrategy<Content, Content> getContextStrategy() {
    return contextStrategy;
  }

  public TreeRelation<Content> getTreeRelation() {
    return treeRelation;
  }

  public ConversionService getConversionService() {
    return conversionService;
  }
}
