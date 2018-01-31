package com.coremedia.caas.link;

import com.coremedia.caas.config.loader.ConfigResourceLoader;
import com.coremedia.caas.config.reader.YamlConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class LinkBuilderReader extends YamlConfigReader {

  private static final Logger LOG = LoggerFactory.getLogger(LinkBuilderReader.class);


  private ApplicationContext applicationContext;


  public LinkBuilderReader(ConfigResourceLoader resourceLoader, ApplicationContext applicationContext) {
    super(resourceLoader);
    this.applicationContext = applicationContext;
  }


  @SuppressWarnings("unchecked")
  public LinkBuilderRegistry read() throws IOException {
    Constructor constructor = new Constructor();
    Yaml yaml = new Yaml(constructor);

    Map<String, String> builderNameMap = (Map<String, String>) yaml.load(getResource("link/link.yml").asString());

    return new LinkBuilderRegistry(
            builderNameMap.entrySet().stream()
                    .peek(e -> LOG.debug("Registering link builder for name {}={}", e.getKey(), e.getValue()))
                    .collect(Collectors.collectingAndThen(Collectors.toMap(Map.Entry::getKey, (Map.Entry<String, String> e) -> applicationContext.getBean(e.getValue(), LinkBuilder.class)),
                                                          Collections::unmodifiableMap))
    );
  }
}
