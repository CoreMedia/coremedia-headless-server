package com.coremedia.caas.server.service.media;

import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.repository.content.model.ContentModelFactory;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.transform.TransformImageService;
import com.coremedia.transform.NamedTransformBeanBlobTransformer;

public class ContentMediaResourceModelFactory implements ContentModelFactory<ContentMediaResourceModel>, MediaResourceModelFactory {

  private ImageVariantsResolver imageVariantsResolver;
  private NamedTransformBeanBlobTransformer mediaTransformer;
  private TransformImageService transformImageService;


  public ContentMediaResourceModelFactory(ImageVariantsResolver imageVariantsResolver, NamedTransformBeanBlobTransformer mediaTransformer, TransformImageService transformImageService) {
    this.imageVariantsResolver = imageVariantsResolver;
    this.mediaTransformer = mediaTransformer;
    this.transformImageService = transformImageService;
  }


  @Override
  public boolean isQueryModel() {
    return false;
  }

  @Override
  public String getModelName() {
    return MODEL_NAME;
  }

  @Override
  public ContentMediaResourceModel createModel(RootContext rootContext, Content content, Object... arguments) {
    // resolve arguments
    String propertyName = (String) arguments[0];
    // instantiate model
    return new ContentMediaResourceModel(content, propertyName, imageVariantsResolver, mediaTransformer, transformImageService, rootContext);
  }
}
