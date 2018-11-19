package com.coremedia.caas.server.resolver;

import com.coremedia.cap.multisite.Site;

public interface SiteResolver {

  String TENANT_ID = "tenantId";


  Site resolveSite(String tenantId, String siteId, String targetId);
}
