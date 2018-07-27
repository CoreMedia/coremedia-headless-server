package com.coremedia.caas.service.repository;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.service.ServiceConfig;
import com.coremedia.caas.service.request.RequestContext;
import com.coremedia.caas.service.security.AccessControlViolation;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.multisite.Site;

import org.springframework.beans.factory.BeanFactory;

import java.util.List;

public class RootContextFactoryImpl implements RootContextFactory {

  private BeanFactory beanFactory;

  private List<ProxyModelFactory> proxyModelFactories;
  private ContentRepository contentRepository;
  private SettingsService settingsService;
  private ServiceConfig serviceConfig;


  public RootContextFactoryImpl(BeanFactory beanFactory, List<ProxyModelFactory> proxyModelFactories, ContentRepository contentRepository, SettingsService settingsService, ServiceConfig serviceConfig) {
    this.beanFactory = beanFactory;
    this.proxyModelFactories = proxyModelFactories;
    this.contentRepository = contentRepository;
    this.settingsService = settingsService;
    this.serviceConfig = serviceConfig;
  }


  @Override
  public RootContext createRootContext(Site site, Object currentContext, Object target, RequestContext requestContext) throws AccessControlViolation {
    return new RootContextImpl(site, currentContext, target, requestContext, proxyModelFactories, contentRepository, settingsService, serviceConfig, beanFactory);
  }
}
