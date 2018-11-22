package com.coremedia.caas.server.resolver;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.multisite.SitesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Order()
public class SiteByIdResolver implements SiteResolver {

  @Autowired
  protected SitesService sitesService;

  @Autowired
  @Qualifier("settingsService")
  private SettingsService settingsService;


  @Override
  public Site resolveSite(String tenantId, String siteId, String targetId) {
    Site site = sitesService.getSites().stream()
            .filter(e -> tenantId.equals(settingsService.setting(TENANT_ID, String.class, e.getSiteIndicator())))
            .filter(e -> siteId.equals(e.getId()))
            .findFirst().orElse(null);
    if (site != null && site.getSiteRootDocument() != null) {
      return site;
    }
    return null;
  }
}
