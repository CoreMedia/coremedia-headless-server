package com.coremedia.caas.resolver;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.multisite.Site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Order(100)
public class ContentMappingResolver implements TargetResolver {

  public static final String STATIC_MAPPING_KEY = "caasMappings";


  @Autowired
  private SettingsService settingsService;


  @Override
  public Object resolveTarget(Site site, String targetId) {
    Map<String, Content> mappings = settingsService.settingAsMap(STATIC_MAPPING_KEY, String.class, Content.class, site.getSiteIndicator());
    return mappings.get(targetId);
  }
}
