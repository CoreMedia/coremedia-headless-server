package com.coremedia.caas.server.service.media;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.transform.TransformImageService;
import com.coremedia.transform.BlobTransformer;
import com.coremedia.transform.NamedTransformBeanBlobTransformer;
import com.coremedia.transform.impl.ExpressionBasedBeanBlobTransformer;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({
        "classpath:/com/coremedia/cap/transform/transform-services.xml"
})
public class MediaConfig {

  @Bean("contentMediaTransformer")
  public NamedTransformBeanBlobTransformer beanBlobTransformer(BlobTransformer blobTransformer) {
    ExpressionBasedBeanBlobTransformer transformer = new ExpressionBasedBeanBlobTransformer();
    transformer.setBlobTransformer(blobTransformer);
    transformer.setDataExpression("data");
    transformer.setTransformMapExpression("transformMap");
    return transformer;
  }


  @Bean("imageVariantsResolver")
  public ImageVariantsResolver imageVariantsResolver(ContentRepository contentRepository, @Qualifier("settingsService") SettingsService settingsService, @Qualifier("modelMapper") ModelMapper modelMapper) {
    return new ImageVariantsResolver(contentRepository, settingsService, modelMapper);
  }


  @Bean
  public ContentMediaResourceModelFactory contentMediaModelFactory(@Qualifier("imageVariantsResolver") ImageVariantsResolver imageVariantsResolver, @Qualifier("contentMediaTransformer") NamedTransformBeanBlobTransformer mediaTransformer, @Qualifier("transformImageService") TransformImageService transformImageService) {
    return new ContentMediaResourceModelFactory(imageVariantsResolver, mediaTransformer, transformImageService);
  }
}
