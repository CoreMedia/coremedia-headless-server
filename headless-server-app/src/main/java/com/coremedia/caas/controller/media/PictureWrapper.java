package com.coremedia.caas.controller.media;

import com.coremedia.cap.common.Blob;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.struct.Struct;
import com.coremedia.cap.transform.TransformImageService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

class PictureWrapper {

  private Content content;
  private String propertyName;
  private TransformImageService transformImageService;


  PictureWrapper(Content content, String propertyName, TransformImageService transformImageService) {
    this.content = content;
    this.propertyName = propertyName;
    this.transformImageService = transformImageService;
  }


  public Blob getData() {
    return content.getBlobRef(propertyName);
  }

  public Map<String, String> getTransformMap() {
    Map<String, String> transformations;
    Struct localSettings = content.getStruct("localSettings");
    if (localSettings != null) {
      Map<String, Object> structMap = localSettings.getStruct("transforms").getProperties();
      transformations = structMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> (String) e.getValue()));
    } else {
      transformations = new HashMap<>();
    }
    return transformImageService.getTransformationOperations(content, propertyName, transformations);
  }
}
