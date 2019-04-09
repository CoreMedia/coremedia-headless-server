package com.coremedia.caas.server.service.media;

import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.cap.common.Blob;
import com.coremedia.cap.common.CapStructHelper;
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
  private ImageVariantsResolver imageVariantsResolver;
  private NamedTransformBeanBlobTransformer mediaTransformer;
  private TransformImageService transformImageService;
  private RootContext rootContext;


  ContentMediaResourceModel(Content content,
                            String propertyName,
                            ImageVariantsResolver imageVariantsResolver,
                            NamedTransformBeanBlobTransformer mediaTransformer,
                            TransformImageService transformImageService,
                            RootContext rootContext) {
    this.content = content;
    this.propertyName = propertyName;
    this.imageVariantsResolver = imageVariantsResolver;
    this.mediaTransformer = mediaTransformer;
    this.transformImageService = transformImageService;
    this.rootContext = rootContext;
  }


  @Override
  public String getType() {
    return content.getType().getName();
  }

  @Override
  public String getHash() {
    return content.getRepository().getConnection().getCache().get(new MediaResourceHashCacheKey(content, rootContext.getSite(), imageVariantsResolver, transformImageService));
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
              return transformImageService.transformWithDimensions(content, blob, transformedBlob, transformation.getName(), null, bestMatch.getWidth(), bestMatch.getHeight());
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
    Map<String, String> transformations = new HashMap<>();
    Struct localSettings = content.getStruct("localSettings");
    if (localSettings != null) {
      Struct transforms = CapStructHelper.getStruct(localSettings, "transforms");
      if (transforms != null) {
        transformations = transforms.getProperties().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> (String) e.getValue()));
      }
    }
    return transformImageService.getTransformationOperations(content, propertyName, transformations);
  }
}
