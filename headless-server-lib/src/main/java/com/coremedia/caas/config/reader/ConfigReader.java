package com.coremedia.caas.config.reader;

import java.util.List;

public interface ConfigReader {

  ConfigResource getResource(String pattern);

  List<ConfigResource> getResources(String pattern);
}
