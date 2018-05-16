package com.coremedia.caas.services.repository;

public interface ModelFactory {

  String NAVIGATION_MODEL = "navigation";
  String PAGEGRID_MODEL = "pageGrid";
  String SETTINGS_MODEL = "settings";


  <T> T createModel(String modelName, String sourceName, Object target);
}
