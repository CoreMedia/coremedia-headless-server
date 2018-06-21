package com.coremedia.caas.generator.config;

import com.coremedia.cap.content.ContentRepository;

import com.google.common.collect.ImmutableList;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ConfigReader {

  private String configPath;
  private ContentRepository contentRepository;


  public ConfigReader(String configPath, ContentRepository contentRepository) {
    this.configPath = configPath;
    this.contentRepository = contentRepository;
  }


  public SchemaConfig createConfig() throws IOException, InvalidTypeDefinition {
    SchemaConfig schemaConfig = readSchemaConfig();
    List<TypeCustomization> typeCustomizations = readTypeCustomizations();

    schemaConfig.setTypeCustomizations(typeCustomizations);
    schemaConfig.init();
    return schemaConfig;
  }


  private SchemaConfig readSchemaConfig() throws IOException {
    PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
    Resource resource = loader.getResource("file:" + configPath + "/schema.yml");

    Yaml yaml = new Yaml();
    try (InputStream resourceStream = resource.getInputStream()) {
      SchemaConfig schemaConfig = yaml.loadAs(new InputStreamReader(resourceStream), SchemaConfig.class);
      schemaConfig.setContentRepository(contentRepository);
      return schemaConfig;
    }
  }

  private List<TypeCustomization> readTypeCustomizations() throws IOException {
    PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
    Resource[] resources = loader.getResources("file:" + configPath + "/types/customizers/**/*.yml");

    Constructor constructor = new Constructor();
    constructor.addTypeDescription(new TypeDescription(ConstantDefinition.class, new Tag("!Constant")));
    constructor.addTypeDescription(new TypeDescription(CustomFieldDefinition.class, new Tag("!CustomField")));
    constructor.addTypeDescription(new TypeDescription(DirectiveDefinition.class, new Tag("!Directive")));
    Yaml yaml = new Yaml(constructor);
    ImmutableList.Builder<TypeCustomization> builder = ImmutableList.builder();
    if (resources != null) {
      for (Resource resource : resources) {
        try (InputStream resourceStream = resource.getInputStream()) {
          builder.add(yaml.loadAs(new InputStreamReader(resourceStream), TypeCustomization.class));
        }
      }
    }
    return builder.build();
  }
}
