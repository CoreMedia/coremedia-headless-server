package com.coremedia.caas.richtext;

import com.coremedia.caas.config.loader.ConfigResourceLoader;
import com.coremedia.caas.richtext.stax.StaxRichtextTransformer;
import com.coremedia.caas.richtext.stax.config.StaxTransformerReader;

import java.io.IOException;
import java.util.List;

public class RichtextTransformerReader {

  private ConfigResourceLoader resourceLoader;


  public RichtextTransformerReader(ConfigResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }


  public RichtextTransformerRegistry read() throws IOException {
    List<StaxRichtextTransformer> transformers = new StaxTransformerReader(resourceLoader).readTransformationConfigs();
    return new RichtextTransformerRegistry(transformers);
  }
}
