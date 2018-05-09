package com.coremedia.caas.services.repository.content;

import com.coremedia.blueprint.base.navigation.context.ContextStrategy;
import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGrid;
import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGridService;
import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.blueprint.base.tree.TreeRelation;
import com.coremedia.caas.services.repository.ProxyModelFactory;
import com.coremedia.caas.services.repository.RootContext;
import com.coremedia.caas.services.repository.content.model.NavigationAdapter;
import com.coremedia.caas.services.repository.content.model.PageGridAdapter;
import com.coremedia.caas.services.repository.content.model.SettingsAdapter;
import com.coremedia.cap.content.Content;

import java.util.Map;

import static com.coremedia.caas.services.repository.ModelFactory.NAVIGATION_MODEL;
import static com.coremedia.caas.services.repository.ModelFactory.PAGEGRID_MODEL;
import static com.coremedia.caas.services.repository.ModelFactory.SETTINGS_MODEL;

public class ContentModelAdapterFactory implements ProxyModelFactory {

  private ContentBackedPageGridService contentBackedPageGridService;
  private ContextStrategy<Content, Content> contextStrategy;
  private Map<String, TreeRelation<Content>> treeRelations;
  private SettingsService settingsService;


  public ContentModelAdapterFactory(ContentBackedPageGridService contentBackedPageGridService, ContextStrategy<Content, Content> contextStrategy, Map<String, TreeRelation<Content>> treeRelations, SettingsService settingsService) {
    this.contentBackedPageGridService = contentBackedPageGridService;
    this.contextStrategy = contextStrategy;
    this.treeRelations = treeRelations;
    this.settingsService = settingsService;
  }


  private ContextStrategy<Content, Content> getContextStrategy(Content content) {
    return contextStrategy;
  }

  private TreeRelation<Content> getTreeRelation(Content content) {
    if (content.getType().isSubtypeOf("CMLocTaxonomy")) {
      return treeRelations.get("locationTaxonomyTreeRelation");
    }
    else if (content.getType().isSubtypeOf("CMTaxonomy")) {
      return treeRelations.get("taxonomyTreeRelation");
    }
    else {
      return treeRelations.get("navigationTreeRelation");
    }
  }


  @Override
  public boolean appliesTo(String modelName, String propertyPath, Object source, RootContext rootContext) {
    return (source instanceof ContentProxyImpl);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T createModel(String modelName, String propertyPath, Object source, RootContext rootContext) {
    Content content = ((ContentProxyImpl) source).getContent();
    switch (modelName) {
      case NAVIGATION_MODEL: {
        return (T) new NavigationAdapter(content, getContextStrategy(content), getTreeRelation(content), rootContext);
      }
      case PAGEGRID_MODEL: {
        ContentBackedPageGrid pageGrid = contentBackedPageGridService.getContentBackedPageGrid(content, propertyPath);
        return (T) new PageGridAdapter(pageGrid, rootContext);
      }
      case SETTINGS_MODEL: {
        return (T) new SettingsAdapter(content, settingsService, rootContext);
      }
      default:
        throw new IllegalArgumentException("Undefined model adapter: " + modelName);
    }
  }
}
