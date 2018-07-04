package com.coremedia.caas.server;

import com.coremedia.caas.service.ServiceConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "caas")
public class CaasServiceConfig implements ServiceConfig {

  private boolean isPreview;
  private boolean isLogRequests;
  private boolean isPrettyPrintOutput;
  private Map<String, Long> cacheCapacities;


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

  public Map<String, Long> getCacheCapacities() {
    return cacheCapacities;
  }

  public void setCacheCapacities(Map<String, Long> cacheCapacities) {
    this.cacheCapacities = cacheCapacities;
  }
}
