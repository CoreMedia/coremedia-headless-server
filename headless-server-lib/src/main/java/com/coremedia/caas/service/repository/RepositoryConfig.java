package com.coremedia.caas.service.repository;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.service.ServiceConfig;
import com.coremedia.caas.service.repository.content.ContentProxyModelAccessor;
import com.coremedia.cap.content.ContentRepository;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;

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


  @Bean("spelPropertyAccessors")
  public List<PropertyAccessor> spelPropertyAccessors(ContentProxyModelAccessor contentProxyModelAccessor) {
    return ImmutableList.of(
            contentProxyModelAccessor,
            new MapAccessor(),
            new ReflectivePropertyAccessor());
  }
}
