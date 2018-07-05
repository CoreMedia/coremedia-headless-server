package com.coremedia.caas.config;

import com.coremedia.caas.config.loader.ConfigResourceLoader;
import com.coremedia.caas.config.reader.YamlConfigReader;
import com.coremedia.caas.link.LinkBuilder;
import com.coremedia.caas.query.QueryReader;
import com.coremedia.caas.query.QueryRegistry;
import com.coremedia.caas.richtext.RichtextTransformerReader;
import com.coremedia.caas.richtext.RichtextTransformerRegistry;
import com.coremedia.caas.schema.SchemaReader;
import com.coremedia.caas.schema.SchemaService;
import com.coremedia.cap.content.ContentRepository;

import org.springframework.context.ApplicationContext;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.IOException;

public class ProcessingDefinitionReader extends YamlConfigReader {

  private String name;
  private ApplicationContext applicationContext;
  private ContentRepository contentRepository;


  ProcessingDefinitionReader(String name, ApplicationContext applicationContext, ContentRepository contentRepository, ConfigResourceLoader resourceLoader) {
    super(resourceLoader);
    this.name = name;
    this.applicationContext = applicationContext;
    this.contentRepository = contentRepository;
  }


  public ProcessingDefinition read() throws IOException {
    Constructor constructor = new Constructor() {
      {
        this.yamlConstructors.put(new Tag("!LinkBuilder"), new ConstructLinkBuilder());
      }

      class ConstructLinkBuilder extends AbstractConstruct {
        @Override
        public Object construct(Node node) {
          return applicationContext.getBean((String) constructScalar((ScalarNode) node), LinkBuilder.class);
        }
      }
    };
    Yaml yaml = new Yaml(constructor);
    ProcessingDefinition definition = yaml.loadAs(getResource("definition.yml").asString(), ProcessingDefinition.class);
    // set requested name
    definition.setName(name);
    // add GraphQL schema
    SchemaService schemaService = new SchemaReader(getResourceLoader()).read(contentRepository);
    definition.setSchemaService(schemaService);
    // add GraphQL queries
    QueryRegistry queryRegistry = new QueryReader(getResourceLoader()).read(schemaService);
    definition.setQueryRegistry(queryRegistry);
    // add richtext transformation
    RichtextTransformerRegistry richtextTransformerRegistry = new RichtextTransformerReader(getResourceLoader()).read();
    definition.setRichtextTransformerRegistry(richtextTransformerRegistry);
    return definition;
  }
}
