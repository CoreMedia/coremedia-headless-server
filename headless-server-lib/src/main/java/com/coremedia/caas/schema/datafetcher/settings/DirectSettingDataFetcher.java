package com.coremedia.caas.schema.datafetcher.settings;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.schema.datafetcher.common.AbstractDataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class DirectSettingDataFetcher extends AbstractDataFetcher {

  @Override
  public Object get(DataFetchingEnvironment environment) {
    SettingsService settingsService = getContext(environment).getServiceRegistry().getSettingsService();
    String key = environment.getArgument("key");
    Object defaultValue = environment.getArgument("default");
    if (key != null) {
      return settingsService.settingWithDefault(key, Object.class, defaultValue, (Object) environment.getSource());
    }
    return null;
  }
}
