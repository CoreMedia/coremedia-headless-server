package com.coremedia.caas.service.repository;

public interface ModelFactory {

  String EXTENDEDLINKLIST_MODEL = "extendedLinks";
  String NAVIGATION_MODEL = "navigation";
  String PAGEGRID_MODEL = "pageGrid";
  String SETTINGS_MODEL = "settings";


  <T> T createModel(String modelName, Object target, Object... arguments);
}
