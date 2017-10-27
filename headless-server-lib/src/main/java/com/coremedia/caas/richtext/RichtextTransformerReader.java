package com.coremedia.caas.richtext;

import com.coremedia.caas.config.loader.ConfigResourceLoader;
import com.coremedia.caas.richtext.stax.StaxRichtextTransformer;
import com.coremedia.caas.richtext.stax.config.StaxConfigReader;
import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.util.List;

public class RichtextTransformerReader {

  private ConfigResourceLoader resourceLoader;


  public RichtextTransformerReader(ConfigResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }


  public RichtextTransformerRegistry read() throws IOException {
    ImmutableList.Builder<RichtextTransformer> builder = ImmutableList.builder();
    List<StaxTransformationConfig<?>> configs = new StaxConfigReader(resourceLoader).readTransformationConfigs();
    for (StaxTransformationConfig<?> config : configs) {
      builder.add(new StaxRichtextTransformer<>(config));
    }
    return new RichtextTransformerRegistry(builder.build());
  }
}
