package com.coremedia.caas.services.repository.content.model.adapter;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.services.repository.RootContext;
import com.coremedia.cap.content.Content;

import java.util.List;

public class SettingsAdapter {

  private Content content;
  private SettingsService settingsService;
  private RootContext rootContext;


  public SettingsAdapter(Content content, SettingsService settingsService, RootContext rootContext) {
    this.content = content;
    this.settingsService = settingsService;
    this.rootContext = rootContext;
  }


  public Object getSetting(List<String> keys, Object defaultValue) {
    Object value = settingsService.nestedSetting(keys, Object.class, content);
    return rootContext.getProxyFactory().makeProxy(value != null ? value : defaultValue);
  }
}
