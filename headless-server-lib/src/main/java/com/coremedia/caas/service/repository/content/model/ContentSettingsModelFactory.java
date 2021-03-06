package com.coremedia.caas.service.repository.content.model;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.repository.content.model.adapter.SettingsAdapter;
import com.coremedia.cap.content.Content;

import static com.coremedia.caas.service.repository.ModelFactory.SETTINGS_MODEL;

public class ContentSettingsModelFactory implements ContentModelFactory<SettingsAdapter> {

  private SettingsService settingsService;


  public ContentSettingsModelFactory(SettingsService settingsService) {
    this.settingsService = settingsService;
  }


  @Override
  public boolean isQueryModel() {
    return false;
  }

  @Override
  public String getModelName() {
    return SETTINGS_MODEL;
  }

  @Override
  public SettingsAdapter createModel(RootContext rootContext, Content content, Object... arguments) {
    return new SettingsAdapter(content, settingsService, rootContext);
  }
}
