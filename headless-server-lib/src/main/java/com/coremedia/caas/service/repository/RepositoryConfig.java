package com.coremedia.caas.service.repository;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.service.ServiceConfig;
import com.coremedia.caas.service.repository.content.ContentProxyPropertyAccessor;
import com.coremedia.caas.service.repository.content.StructProxyPropertyAccessor;
import com.coremedia.cap.content.ContentRepository;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.List;

@Configuration
public class RepositoryConfig {

  @Bean("proxyFactory")
  @Scope(BeanDefinition.SCOPE_PROTOTYPE)
  public ProxyFactory proxyFactory(ContentRepository contentRepository, RootContext rootContext) {
    return new ProxyFactoryImpl(contentRepository, rootContext);
  }

  @Bean("rootContextFactory")
  public RootContextFactory rootContextFactory(BeanFactory beanFactory, List<ProxyModelFactory> proxyModelFactories, ContentRepository contentRepository, SettingsService settingsService, ServiceConfig serviceConfig) {
    return new RootContextFactoryImpl(beanFactory, proxyModelFactories, contentRepository, settingsService, serviceConfig);
  }


  @Bean("schemaEvaluationContext")
  public EvaluationContext schemaEvaluationContext(@Qualifier("schemaContentModelMethodResolver") MethodResolver contentMethodResolver) {
    List<PropertyAccessor> propertyAccessors = ImmutableList.of(
            new ContentProxyPropertyAccessor(),
            new StructProxyPropertyAccessor(),
            new MapAccessor(),
            new ReflectivePropertyAccessor());
    // customize evaluation context
    StandardEvaluationContext context = new StandardEvaluationContext();
    context.setPropertyAccessors(propertyAccessors);
    context.addMethodResolver(contentMethodResolver);
    return context;
  }
}
