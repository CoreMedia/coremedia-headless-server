package com.coremedia.caas.richtext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

public class RichtextTransformerRegistry {

  private static final Logger LOG = LoggerFactory.getLogger(RichtextTransformerRegistry.class);


  private Map<String, RichtextTransformer> transformerMapping;


  public RichtextTransformerRegistry(@NotNull List<? extends RichtextTransformer> transformers) {
    this.transformerMapping = transformers.stream()
            .peek(e -> LOG.debug("Registering richtext transformer for view {}", e.getView()))
            .collect(Collectors.collectingAndThen(Collectors.toMap(RichtextTransformer::getView, Function.identity()), Collections::unmodifiableMap));
  }


  public RichtextTransformer getTransformer(String view) {
    return transformerMapping.get(view);
  }
}
