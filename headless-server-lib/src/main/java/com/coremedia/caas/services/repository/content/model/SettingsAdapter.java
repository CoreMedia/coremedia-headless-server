package com.coremedia.caas.services.repository.content.model;

import java.util.List;

public interface SettingsAdapter {

  Object getSetting(List<String> keys, Object defaultValue);
}
