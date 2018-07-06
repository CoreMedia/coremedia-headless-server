package com.coremedia.caas.service.repository.content.model.adapter;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.cap.content.Content;

import com.google.common.base.Splitter;

public class SettingsAdapter {

  private static final Splitter PATH_SPLITTER = Splitter.on('/').omitEmptyStrings();

  private Content content;
  private SettingsService settingsService;
  private RootContext rootContext;

  public SettingsAdapter(Content content, SettingsService settingsService, RootContext rootContext) {
    this.content = content;
    this.settingsService = settingsService;
    this.rootContext = rootContext;
  }

  public Object getSetting(String sourceName, Object defaultValue) {
    Object value = settingsService.nestedSetting(PATH_SPLITTER.splitToList(sourceName), Object.class, content);
    return rootContext.getProxyFactory().makeProxy(value != null ? value : defaultValue);
  }
}
