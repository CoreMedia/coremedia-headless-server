package com.coremedia.caas.service.repository;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.service.ServiceConfig;
import com.coremedia.caas.service.request.RequestContext;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.multisite.Site;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.util.List;

public class RootContextFactoryImpl implements RootContextFactory, BeanFactoryAware {

  private BeanFactory beanFactory;

  private List<ProxyModelFactory> proxyModelFactories;
  private ContentRepository contentRepository;
  private SettingsService settingsService;
  private ServiceConfig serviceConfig;


  public RootContextFactoryImpl(List<ProxyModelFactory> proxyModelFactories, ContentRepository contentRepository, SettingsService settingsService, ServiceConfig serviceConfig) {
    this.proxyModelFactories = proxyModelFactories;
    this.contentRepository = contentRepository;
    this.settingsService = settingsService;
    this.serviceConfig = serviceConfig;
  }


  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }


  @Override
  public RootContext createRootContext(Site site, Object currentContext, Object target, RequestContext requestContext) {
    return beanFactory.getBean(RootContext.class, site, currentContext, target, requestContext, proxyModelFactories, contentRepository, settingsService, serviceConfig);
  }
}
