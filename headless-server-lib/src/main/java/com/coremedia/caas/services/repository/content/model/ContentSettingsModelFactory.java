package com.coremedia.caas.services.repository.content.model;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.services.repository.RootContext;
import com.coremedia.caas.services.repository.content.model.adapter.SettingsAdapter;
import com.coremedia.cap.content.Content;

import static com.coremedia.caas.services.repository.ModelFactory.SETTINGS_MODEL;

public class ContentSettingsModelFactory implements ContentModelFactory<SettingsAdapter> {

  private SettingsService settingsService;


  public ContentSettingsModelFactory(SettingsService settingsService) {
    this.settingsService = settingsService;
  }


  @Override
  public String getModelName() {
    return SETTINGS_MODEL;
  }

  @Override
  public SettingsAdapter createModel(Content content, String propertyPath, RootContext rootContext) {
    return new SettingsAdapter(content, settingsService, rootContext);
  }
}
