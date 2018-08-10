package com.coremedia.caas.service.repository;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.service.ServiceConfig;
import com.coremedia.caas.service.request.RequestContext;
import com.coremedia.caas.service.security.AccessControl;
import com.coremedia.caas.service.security.AccessControlViolation;
import com.coremedia.caas.service.security.AccessValidator;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.struct.Struct;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RootContextImpl implements RootContext {

  private Site site;

  private Object currentContext;
  private Object target;
  private Object proxyTarget;

  private ContentRepository contentRepository;
  private SettingsService settingsService;

  private ServiceConfig serviceConfig;

  private RequestContext requestContext;

  private AccessControl accessControl;
  private ModelFactory modelFactory;
  private ProxyFactory proxyFactory;

  private List<ProxyModelFactory> proxyModelFactories;


  public RootContextImpl(Site site, Object currentContext, Object target, RequestContext requestContext, List<ProxyModelFactory> proxyModelFactories, ContentRepository contentRepository, SettingsService settingsService, ServiceConfig serviceConfig, BeanFactory beanFactory) throws AccessControlViolation {
    this.site = site;
    this.currentContext = currentContext;
    this.target = target;
    this.requestContext = requestContext;
    this.proxyModelFactories = proxyModelFactories;
    this.contentRepository = contentRepository;
    this.settingsService = settingsService;
    this.serviceConfig = serviceConfig;
    init(beanFactory);
  }


  private void init(BeanFactory beanFactory) throws AccessControlViolation {
    // init access control
    List<String> accessValidatorNames = null;
    // fetch access control struct from site indicator settings
    Struct accessSettings = settingsService.nestedSetting(ImmutableList.of("caasSettings", "accessControl", serviceConfig.isPreview() ? "preview" : "live"), Struct.class, site.getSiteIndicator());
    if (accessSettings != null) {
      accessValidatorNames = accessSettings.getStrings("validators");
    }
    if (accessValidatorNames == null || accessValidatorNames.isEmpty()) {
      accessValidatorNames = serviceConfig.getDefaultValidators();
    }
    List<AccessValidator> accessValidators = accessValidatorNames.stream().filter(Objects::nonNull).map(e -> beanFactory.getBean(e, AccessValidator.class)).collect(Collectors.toList());
    accessControl = new CompoundAccessControl(accessValidators, this);
    // init proxy factory
    proxyFactory = beanFactory.getBean(ProxyFactory.class, contentRepository, this);
    // init model factory
    modelFactory = new CompoundModelFactory(proxyModelFactories, this);
    // validate root object
    accessControl.checkAndThrow(target);
    proxyTarget = proxyFactory.makeRoot(target);
  }


  @Override
  public Site getSite() {
    return site;
  }

  @Override
  public Object getCurrentContext() {
    return currentContext;
  }

  @Override
  public Object getTarget() {
    return proxyTarget;
  }


  @Override
  public AccessControl getAccessControl() {
    return accessControl;
  }

  @Override
  public ModelFactory getModelFactory() {
    return modelFactory;
  }

  @Override
  public ProxyFactory getProxyFactory() {
    return proxyFactory;
  }


  @Override
  public RequestContext getRequestContext() {
    return requestContext;
  }


  /*
   * Access Control Container
   */

  private static class CompoundAccessControl implements AccessControl {

    private static final Logger LOG = LoggerFactory.getLogger(CompoundAccessControl.class);

    private List<AccessValidator> accessValidators;
    private RootContext rootContext;

    private CompoundAccessControl(List<AccessValidator> accessValidators, RootContext rootContext) {
      this.accessValidators = accessValidators;
      this.rootContext = rootContext;
    }

    @SuppressWarnings("unchecked")
    private boolean checkInternal(Object target, boolean raiseException) throws AccessControlViolation {
      if (accessValidators != null && !accessValidators.isEmpty()) {
        for (AccessValidator validator : accessValidators) {
          if (validator.appliesTo(target) && !validator.validate(target, rootContext)) {
            if (raiseException) {
              throw new AccessControlViolation(validator.getErrorCode());
            }
            return false;
          }
        }
      }
      return true;
    }

    @Override
    public boolean check(Object target) {
      try {
        return checkInternal(target, false);
      } catch (AccessControlViolation e) {
        LOG.error("Internal check raised exception", e);
        return false;
      }
    }

    @Override
    public void checkAndThrow(Object target) throws AccessControlViolation {
      checkInternal(target, true);
    }
  }


  /*
   * Model Factory Container
   */

  private static class CompoundModelFactory implements ModelFactory {

    private List<ProxyModelFactory> proxyModelFactories;
    private RootContext rootContext;

    private CompoundModelFactory(List<ProxyModelFactory> proxyModelFactories, RootContext rootContext) {
      this.proxyModelFactories = proxyModelFactories;
      this.rootContext = rootContext;
    }

    @Override
    public <T> T createModel(String modelName, String sourceName, Object target) {
      for (ProxyModelFactory proxyModelFactory : proxyModelFactories) {
        if (proxyModelFactory.appliesTo(modelName, sourceName, target, rootContext)) {
          return proxyModelFactory.createModel(modelName, sourceName, target, rootContext);
        }
      }
      return null;
    }
  }
}
