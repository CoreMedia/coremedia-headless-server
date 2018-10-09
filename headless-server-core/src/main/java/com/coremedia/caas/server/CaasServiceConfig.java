package com.coremedia.caas.server;

import com.coremedia.caas.service.ServiceConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "caas")
public class CaasServiceConfig implements ServiceConfig {

  private boolean isPreview;
  private boolean isLogRequests;
  private boolean isPrettyPrintOutput;
  private boolean isBinaryUriHashesEnabled;
  private Map<String, String> cacheSpecs = new HashMap<>();
  private Map<String, Long> cacheCapacities = new HashMap<>();
  private AccessControlConfig accessControlConfig;
  private long cacheTime;
  private long mediaCacheTime;
  private LinkedHashMap<MediaType, Long> mediaCacheTimes = new LinkedHashMap<>();


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

  public boolean isBinaryUriHashesEnabled() {
    return isBinaryUriHashesEnabled;
  }

  public void setBinaryUriHashesEnabled(boolean binaryUriHashesEnabled) {
    isBinaryUriHashesEnabled = binaryUriHashesEnabled;
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

  public long getCacheTime() {
    return cacheTime;
  }

  public void setCacheTime(long cacheTime) {
    this.cacheTime = cacheTime;
  }

  public long getMediaCacheTime() {
    return mediaCacheTime;
  }

  public void setMediaCacheTime(long mediaCacheTime) {
    this.mediaCacheTime = mediaCacheTime;
  }

  public Map<MediaType, Long> getMediaCacheTimes() {
    return mediaCacheTimes;
  }

  @SuppressWarnings("unused")
  public void addMediaCacheTimes(MediaType mediaType, Long cacheTime) {
    mediaCacheTimes.put(mediaType, cacheTime);
  }

  public long getMediaCacheTime(MediaType mediaType) {
    for (Map.Entry<MediaType, Long> entry : mediaCacheTimes.entrySet()) {
      if (entry.getKey().includes(mediaType)) {
        return entry.getValue();
      }
    }
    return mediaCacheTime;
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
