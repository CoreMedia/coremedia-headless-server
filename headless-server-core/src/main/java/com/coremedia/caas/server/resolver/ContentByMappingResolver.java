package com.coremedia.caas.server.resolver;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.multisite.Site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Order(10)
public class ContentByMappingResolver implements TargetResolver {

  public static final String STATIC_MAPPING_KEY = "caasMappings";


  @Autowired
  private SettingsService settingsService;


  @Override
  public Object resolveTarget(Site site, String targetId) {
    Map<String, Object> mappings = settingsService.settingAsMap(STATIC_MAPPING_KEY, String.class, Object.class, site.getSiteIndicator());
    // be sure to cope with editorial errors in the struct, e.g. adding non link values
    Object target = mappings.get(targetId);
    if(target instanceof Content) {
      return target;
    }
    return null;
  }
}
