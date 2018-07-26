package com.coremedia.caas.server;

import com.coremedia.caas.service.ServiceConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "caas")
public class CaasServiceConfig implements ServiceConfig {

  private boolean isPreview;
  private boolean isLogRequests;
  private boolean isPrettyPrintOutput;
  private Map<String, String> cacheSpecs = new HashMap<>();
  private Map<String, Long> cacheCapacities = new HashMap<>();
  private AccessControlConfig accessControlConfig;


  @Override
  public boolean isPreview() {
    return isPreview;
  }

  public void setPreview(boolean preview) {
    isPreview = preview;
  }

  public boolean isLogRequests() {
    return isLogRequests;
  }

  public void setLogRequests(boolean logRequests) {
    isLogRequests = logRequests;
  }

  public boolean isPrettyPrintOutput() {
    return isPrettyPrintOutput;
  }

  public void setPrettyPrintOutput(boolean prettyPrintOutput) {
    isPrettyPrintOutput = prettyPrintOutput;
  }

  public Map<String, String> getCacheSpecs() {
    return cacheSpecs;
  }

  public void setCacheSpecs(Map<String, String> cacheSpecs) {
    this.cacheSpecs = cacheSpecs;
  }

  public Map<String, Long> getCacheCapacities() {
    return cacheCapacities;
  }

  public void setCacheCapacities(Map<String, Long> cacheCapacities) {
    this.cacheCapacities = cacheCapacities;
  }

  public AccessControlConfig getAccessControl() {
    return accessControlConfig;
  }

  public void setAccessControl(AccessControlConfig accessControlConfig) {
    this.accessControlConfig = accessControlConfig;
  }


  @Override
  public List<String> getDefaultValidators() {
    if (accessControlConfig != null) {
      return accessControlConfig.getDefaultValidators();
    }
    return Collections.emptyList();
  }


  public static class AccessControlConfig {

    private List<String> defaultValidators = new ArrayList<>();

    public List<String> getDefaultValidators() {
      return defaultValidators;
    }

    public void setDefaultValidators(List<String> defaultValidators) {
      this.defaultValidators = defaultValidators;
    }
  }
}
