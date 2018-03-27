package com.coremedia.caas.services;

import com.coremedia.blueprint.base.navigation.context.ContextStrategy;
import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGridService;
import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.blueprint.base.tree.TreeRelation;
import com.coremedia.caas.services.expression.ExpressionEvaluator;
import com.coremedia.caas.services.expression.spel.SpelExpressionEvaluator;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class ServiceConfig {

  @Bean("spelEvaluator")
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
  public ExpressionEvaluator createSpelExpressionEvaluator() {
    return new SpelExpressionEvaluator();
  }


  @Bean
  public ServiceRegistry createServiceRegistry(
          ContentRepository contentRepository,
          SettingsService settingsService,
          ContentBackedPageGridService contentBackedPageGridService,
          ContextStrategy<Content, Content> contextStrategy,
          @Qualifier("navigationTreeRelation") TreeRelation<Content> treeRelation,
          @Qualifier("dataFetcherConversionService") ConversionService conversionService,
          @Qualifier("spelEvaluator") ExpressionEvaluator expressionEvaluator) {

    return new ServiceRegistry(contentRepository, settingsService, contentBackedPageGridService, contextStrategy, treeRelation, conversionService, expressionEvaluator);
  }
}
