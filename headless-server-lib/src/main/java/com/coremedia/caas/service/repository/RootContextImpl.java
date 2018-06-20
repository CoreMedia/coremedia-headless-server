package com.coremedia.caas.service.repository;

import com.coremedia.caas.service.request.RequestContext;
import com.coremedia.caas.service.security.AccessControl;
import com.coremedia.caas.service.security.AccessControlViolation;
import com.coremedia.caas.service.security.AccessValidator;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.multisite.Site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.util.List;
import javax.annotation.PostConstruct;

public class RootContextImpl implements RootContext, BeanFactoryAware {

  private BeanFactory beanFactory;

  private ContentRepository contentRepository;

  private Site site;

  private Object currentContext;
  private Object target;
  private Object proxyTarget;

  private RequestContext requestContext;

  private AccessControl accessControl;
  private ModelFactory modelFactory;
  private ProxyFactory proxyFactory;

  private List<AccessValidator> accessValidators;
  private List<ProxyModelFactory> proxyModelFactories;


  public RootContextImpl(Site site, Object currentContext, Object target, RequestContext requestContext, List<AccessValidator> accessValidators, List<ProxyModelFactory> proxyModelFactories, ContentRepository contentRepository) {
    this.site = site;
    this.currentContext = currentContext;
    this.target = target;
    this.requestContext = requestContext;
    this.accessValidators = accessValidators;
    this.proxyModelFactories = proxyModelFactories;
    this.contentRepository = contentRepository;
  }


  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }


  @PostConstruct
  private void init() throws AccessControlViolation {
    proxyFactory = beanFactory.getBean(ProxyFactory.class, contentRepository, this);
    // init access control and model factory
    accessControl = new CompoundAccessControl(accessValidators, this);
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
