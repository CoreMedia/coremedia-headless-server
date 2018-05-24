package com.coremedia.caas.controller.base;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.monitoring.Metrics;
import com.coremedia.caas.resolver.TargetResolver;
import com.coremedia.caas.services.repository.RootContext;
import com.coremedia.caas.services.repository.RootContextFactory;
import com.coremedia.caas.services.request.RequestContext;
import com.coremedia.caas.services.security.AccessControlViolation;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.multisite.SitesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ControllerBase {

  private static final String TENANT_ID = "tenantId";


  private String timerName;

  @Autowired
  private Metrics metrics;

  @Autowired
  protected RequestContext requestContext;

  @Autowired
  protected RootContextFactory rootContextFactory;

  @Autowired
  protected SitesService siteService;

  @Autowired
  @Qualifier("settingsService")
  private SettingsService settingsService;

  @Autowired
  private List<TargetResolver> targetResolvers;


  public ControllerBase(String timerName) {
    this.timerName = timerName;
  }


  private List<Site> resolveSites(String tenantId) {
    return siteService.getSites().stream().filter(site -> tenantId.equals(settingsService.setting(TENANT_ID, String.class, site))).collect(Collectors.toList());
  }

  private Site resolveSite(String tenantId, String siteId) {
    Site site = resolveSites(tenantId).stream().filter(item -> siteId.equals(item.getId())).findFirst().orElse(null);
    if (site != null && site.getSiteRootDocument() != null) {
      return site;
    }
    return null;
  }

  private Object resolveTarget(Site localizedSite, String targetId) {
    for (TargetResolver resolver : targetResolvers) {
      Object target = resolver.resolveTarget(localizedSite, targetId);
      if (target != null) {
        if (validateTarget(localizedSite, target)) {
          return target;
        }
        return null;
      }
    }
    return null;
  }

  private boolean validateTarget(Site localizedSite, Object target) {
    return !(target instanceof Content) || siteService.isContentInSite(localizedSite, (Content) target);
  }


  protected RootContext resolveRootContext(String tenantId, String siteId, HttpServletRequest request, HttpServletResponse response) throws AccessControlViolation {
    Site site = resolveSite(tenantId, siteId);
    if (site == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    // site indicator itself is the query root
    return rootContextFactory.createRootContext(site.getSiteIndicator(), site.getSiteRootDocument(), null, site.getSiteIndicator(), requestContext);
  }

  protected RootContext resolveRootContext(String tenantId, String siteId, String targetId, HttpServletRequest request, HttpServletResponse response) throws AccessControlViolation {
    Site site = resolveSite(tenantId, siteId);
    if (site == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    // resolve query root
    Object target = resolveTarget(site, targetId);
    if (target == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    return rootContextFactory.createRootContext(site.getSiteIndicator(), site.getSiteRootDocument(), null, target, requestContext);
  }


  protected <E> E execute(Supplier<E> supplier, String... tags) {
    return metrics.timer(supplier, timerName, tags);
  }


  protected ResponseEntity handleError(Exception error, HttpServletRequest request, HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    return null;
  }

  protected ResponseEntity handleError(AccessControlViolation error, HttpServletRequest request, HttpServletResponse response) {
    switch (error.getErrorCode()) {
      case INVALID_OBJECT:
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return null;
      case INSUFFICIENT_RIGHTS:
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return null;
      default:
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return null;
    }
  }
}
