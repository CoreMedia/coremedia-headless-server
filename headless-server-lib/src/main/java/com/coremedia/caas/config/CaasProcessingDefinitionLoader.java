package com.coremedia.caas.config;

import com.coremedia.caas.config.loader.ConfigResourceLoader;
import com.coremedia.caas.link.LinkBuilderReader;
import com.coremedia.caas.link.LinkBuilderRegistry;
import com.coremedia.caas.query.QueryReader;
import com.coremedia.caas.query.QueryRegistry;
import com.coremedia.caas.richtext.RichtextTransformerReader;
import com.coremedia.caas.richtext.RichtextTransformerRegistry;
import com.coremedia.caas.schema.InvalidDefinition;
import com.coremedia.caas.schema.SchemaReader;
import com.coremedia.caas.schema.SchemaService;
import com.coremedia.cap.content.ContentRepository;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class CaasProcessingDefinitionLoader {

  private String name;
  private ConfigResourceLoader resourceLoader;
  private ContentRepository contentRepository;
  private ApplicationContext applicationContext;


  public CaasProcessingDefinitionLoader(String name, ConfigResourceLoader resourceLoader, ContentRepository contentRepository, ApplicationContext applicationContext) {
    this.name = name;
    this.resourceLoader = resourceLoader;
    this.contentRepository = contentRepository;
    this.applicationContext = applicationContext;
  }


  public CaasProcessingDefinition load() throws InvalidDefinition, IOException {
    // GraphQL schema
    SchemaService schemaService = new SchemaReader(resourceLoader).read(contentRepository);
    // GraphQL queries
    QueryRegistry queryRegistry = new QueryReader(resourceLoader).read(schemaService);
    // link builder registry
    LinkBuilderRegistry linkBuilderRegistry = new LinkBuilderReader(resourceLoader, applicationContext).read();
    // richtext transformation registry
    RichtextTransformerRegistry richtextTransformerRegistry = new RichtextTransformerReader(resourceLoader).read();
    // processing configuration definition
    return new CaasProcessingDefinition(name, schemaService, queryRegistry, linkBuilderRegistry, richtextTransformerRegistry);
  }
}
