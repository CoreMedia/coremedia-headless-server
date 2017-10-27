package com.coremedia.caas.config.reader;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface ConfigReader {

  Resource[] getResources(String pattern) throws IOException;

  Resource getResource(String pattern);
}
