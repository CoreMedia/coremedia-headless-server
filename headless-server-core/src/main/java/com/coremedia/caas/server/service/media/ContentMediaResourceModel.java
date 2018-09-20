package com.coremedia.caas.server.service.media;

import com.coremedia.cap.common.Blob;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.struct.Struct;
import com.coremedia.cap.transform.Breakpoint;
import com.coremedia.cap.transform.TransformImageService;
import com.coremedia.cap.transform.Transformation;
import com.coremedia.transform.NamedTransformBeanBlobTransformer;
import com.coremedia.transform.TransformedBlob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

class ContentMediaResourceModel implements MediaResourceModel {

  private static final Logger LOG = LoggerFactory.getLogger(ContentMediaResourceModel.class);


  private Content content;
  private String propertyName;
  private NamedTransformBeanBlobTransformer mediaTransformer;
  private TransformImageService transformImageService;


  ContentMediaResourceModel(Content content, String propertyName, NamedTransformBeanBlobTransformer mediaTransformer, TransformImageService transformImageService) {
    this.content = content;
    this.propertyName = propertyName;
    this.mediaTransformer = mediaTransformer;
    this.transformImageService = transformImageService;
  }


  @Override
  public String getType() {
    return content.getType().getName();
  }

  @Override
  public MediaResource getMediaResource(String ratio, int minWidth, int minHeight) {
    Blob blob = transformBlob(ratio, minWidth, minHeight);
    if (blob != null) {
      return new ContentBlobMediaResource(blob, content, propertyName);
    }
    return null;
  }


  private Blob transformBlob(String ratio, int minWidth, int minHeight) {
    Blob blob = content.getBlob(propertyName);
    if (blob != null) {
      if (content.getType().isSubtypeOf("CMPicture")) {
        if (ratio == null) {
          // always deliver original
          return blob;
        }
        if (mediaTransformer == null) {
          LOG.error("No media transformer configured");
          return blob;
        }
        Transformation transformation = transformImageService.getTransformation(content, ratio);
        if (transformation != null) {
          Breakpoint bestMatch = null;
          for (Breakpoint breakpoint : transformation.getBreakpoints()) {
            if (bestMatch == null) {
              bestMatch = breakpoint;
            }
            else if (bestMatch.getWidth() < minWidth && bestMatch.getWidth() < breakpoint.getWidth()) {
              bestMatch = breakpoint;
            }
            else if (bestMatch.getHeight() < minHeight && bestMatch.getHeight() < breakpoint.getHeight()) {
              bestMatch = breakpoint;
            }
          }
          if (bestMatch != null) {
            TransformedBlob transformedBlob = mediaTransformer.transform(this, transformation.getName());
            if (transformedBlob != null) {
              return transformImageService.transformWithDimensions(content, blob, transformedBlob, transformation.getName(), "jpg", bestMatch.getWidth(), bestMatch.getHeight());
            }
          }
        }
        LOG.warn("No transformation '{}' found for {}", ratio, content);
        return blob;
      }
      else if (content.getType().isSubtypeOf("CMMedia")) {
        return blob;
      }
      else if (content.getType().isSubtypeOf("CMDownload")) {
        return blob;
      }
      else {
        LOG.warn("Unsupported media type requested: {}", content.getType());
        return null;
      }
    }
    return null;
  }


  /*
   * Transformer callbacks
   */

  @SuppressWarnings("unused")
  public Blob getData() {
    return content.getBlob(propertyName);
  }

  @SuppressWarnings("unused")
  public Map<String, String> getTransformMap() {
    Map<String, String> transformations;
    Struct localSettings = content.getStruct("localSettings");
    if (localSettings != null) {
      Map<String, Object> structMap = localSettings.getStruct("transforms").getProperties();
      transformations = structMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> (String) e.getValue()));
    }
    else {
      transformations = new HashMap<>();
    }
    return transformImageService.getTransformationOperations(content, propertyName, transformations);
  }
}
