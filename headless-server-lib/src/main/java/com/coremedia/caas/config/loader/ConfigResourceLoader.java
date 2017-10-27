package com.coremedia.caas.config.loader;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface ConfigResourceLoader {

  Resource getResource(String pattern);

  Resource[] getResources(String pattern) throws IOException;
}
