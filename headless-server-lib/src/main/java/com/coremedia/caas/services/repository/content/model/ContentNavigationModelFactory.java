package com.coremedia.caas.services.repository.content.model;

import com.coremedia.blueprint.base.navigation.context.ContextStrategy;
import com.coremedia.blueprint.base.tree.TreeRelation;
import com.coremedia.caas.services.repository.RootContext;
import com.coremedia.caas.services.repository.content.model.adapter.NavigationAdapter;
import com.coremedia.cap.content.Content;

import java.util.Map;

import static com.coremedia.caas.services.repository.ModelFactory.NAVIGATION_MODEL;

public class ContentNavigationModelFactory implements ContentModelFactory<NavigationAdapter> {

  private ContextStrategy<Content, Content> contextStrategy;
  private Map<String, TreeRelation<Content>> treeRelations;


  public ContentNavigationModelFactory(ContextStrategy<Content, Content> contextStrategy, Map<String, TreeRelation<Content>> treeRelations) {
    this.contextStrategy = contextStrategy;
    this.treeRelations = treeRelations;
  }


  @Override
  public boolean isExpressionModel() {
    return true;
  }

  @Override
  public String getModelName() {
    return NAVIGATION_MODEL;
  }

  @Override
  public NavigationAdapter createModel(Content content, String propertyPath, RootContext rootContext) {
    return new NavigationAdapter(content, getContextStrategy(content), getTreeRelation(content), rootContext);
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
}
