package com.coremedia.caas.config.reader;

import com.coremedia.caas.config.loader.ConfigResourceLoader;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Map;

public class AbstractConfigReader implements ConfigReader {

  private static final String COMMAND_PREFIX = "#!";

  private static final Splitter.MapSplitter COMMAND_SPLITTER = Splitter.on(' ').trimResults().omitEmptyStrings().withKeyValueSeparator('=');


  private ConfigResourceLoader resourceLoader;


  protected AbstractConfigReader(ConfigResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }


  protected String getCmd(String line) {
    if (line.startsWith(COMMAND_PREFIX)) {
      int argIndex = line.indexOf(' ');
      if (argIndex > 0) {
        return line.substring(COMMAND_PREFIX.length(), argIndex);
      }
    }
    return "";
  }

  protected Map<String, String> getCmdArgs(String line) {
    int argIndex = line.indexOf(' ');
    if (argIndex > 0) {
      return COMMAND_SPLITTER.split(line.substring(argIndex));
    }
    return ImmutableMap.of();
  }


  @Override
  public Resource getResource(String pattern) {
    return resourceLoader.getResource(pattern);
  }

  @Override
  public Resource[] getResources(String pattern) throws IOException {
    return resourceLoader.getResources(pattern);
  }
}
