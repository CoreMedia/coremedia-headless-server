package com.coremedia.caas.config.loader;

import com.google.common.io.ByteStreams;
import org.springframework.core.io.Resource;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JarConfigResourceLoader implements ConfigResourceLoader {

  private static AntPathMatcher matcher = new AntPathMatcher();


  private List<BufferedJarResource> resources = new ArrayList<>();


  public JarConfigResourceLoader(JarInputStream jarInputStream) throws IOException {
    JarEntry jarEntry;
    while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
      if (!jarEntry.isDirectory()) {
        resources.add(new BufferedJarResource(jarEntry.getName(), ByteStreams.toByteArray(jarInputStream)));
      }
    }
  }


  @Override
  public Resource getResource(String pattern) {
    return resources.stream().filter(e -> matcher.match(pattern, e.getFilename())).findFirst().orElse(null);
  }

  @Override
  public Resource[] getResources(String pattern) {
    return resources.stream().filter(e -> matcher.match(pattern, e.getFilename())).toArray(Resource[]::new);
  }
}
