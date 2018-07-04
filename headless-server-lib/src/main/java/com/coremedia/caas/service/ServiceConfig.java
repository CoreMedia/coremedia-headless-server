package com.coremedia.caas.service;

import java.util.List;

public interface ServiceConfig {

  boolean isPreview();

  List<String> getDefaultValidators();
}
