package com.coremedia.caas.schema.datafetcher.settings;

import com.coremedia.caas.schema.datafetcher.common.AbstractDataFetcher;

import com.google.common.base.Splitter;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;

public class DirectSettingDataFetcher extends AbstractDataFetcher {

  @Override
  public Object get(DataFetchingEnvironment environment) {
    String key = getArgument("key", environment);
    if (key != null) {
      List<String> keys = Splitter.on(getArgumentWithDefault("separator", '.', environment)).omitEmptyStrings().splitToList(key);
      Object value = getContext(environment).getServiceRegistry().getSettingsService().nestedSetting(keys, Object.class, (Object) environment.getSource());
      if (value != null) {
        return value;
      }
      else {
        return getArgument("default", environment);
      }
    }
    return null;
  }
}
