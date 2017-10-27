package com.coremedia.caas.link;

import com.coremedia.caas.config.loader.ConfigResourceLoader;
import com.coremedia.caas.config.reader.YamlConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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


  public LinkBuilderRegistry read() throws IOException {
    Resource resource = getResource("link/link.yml");

    Constructor constructor = new Constructor();
    Yaml yaml = new Yaml(constructor);

    InputStream resourceStream = resource.getInputStream();
    @SuppressWarnings("unchecked")
    Map<String, String> builderNameMap = (Map<String, String>) yaml.load(new InputStreamReader(resourceStream));

    Map<String, LinkBuilder> builderMap = builderNameMap.entrySet().stream()
            .peek(e -> LOG.debug("Registering link builder for name {}={}", e.getKey(), e.getValue()))
            .collect(Collectors.collectingAndThen(
                    Collectors.toMap(Map.Entry::getKey, (Map.Entry<String, String> e) -> applicationContext.getBean(e.getValue(), LinkBuilder.class)),
                    Collections::unmodifiableMap
            ));
    return new LinkBuilderRegistry(builderMap);
  }
}
