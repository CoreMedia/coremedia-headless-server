package com.coremedia.caas.resolver;

import com.coremedia.cap.multisite.Site;

public interface TargetResolver {

  Object resolveTarget(Site site, String targetId);
}
