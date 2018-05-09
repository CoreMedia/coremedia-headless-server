package com.coremedia.caas.services.repository;

public interface ModelFactory {

  public static final String NAVIGATION_MODEL = "navigation";
  public static final String PAGEGRID_MODEL = "pageGrid";
  public static final String SETTINGS_MODEL = "settings";


  <T> T createModel(String modelName, String sourceName, Object target);
}
