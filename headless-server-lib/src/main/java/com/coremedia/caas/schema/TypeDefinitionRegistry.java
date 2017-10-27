package com.coremedia.caas.schema;

import com.coremedia.cap.content.ContentRepository;
import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLOutputType;
import graphql.schema.TypeResolver;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TypeDefinitionRegistry {

  private SchemaService schemaService;
  private TypeResolver typeResolver;
  private Map<String, TypeDefinition> typeDefinitions;


  TypeDefinitionRegistry(List<TypeDefinition> typeDefinitions) {
    this.typeDefinitions = typeDefinitions.stream().collect(Collectors.toMap(TypeDefinition::getName, Function.identity()));
  }


  TypeResolver getTypeResolver() {
    return typeResolver;
  }


  InterfaceType getInterfaceType(String name) {
    return (InterfaceType) typeDefinitions.get(name);
  }

  ObjectType getObjectType(String name) {
    return (ObjectType) typeDefinitions.get(name);
  }


  SchemaService createSchemaService(ContentRepository contentRepository) throws InvalidTypeDefinition {
    schemaService = new SchemaService();
    typeResolver = new ObjectTypeResolver(schemaService);
    // create GraphQL types for each registered definition
    ImmutableList.Builder<GraphQLOutputType> builder = ImmutableList.builder();
    for (TypeDefinition definition : typeDefinitions.values()) {
      builder.add(definition.build(this));
    }
    List<GraphQLOutputType> types = builder.build();
    // initialize schema type mappings
    schemaService.init(types, contentRepository);
    return schemaService;
  }
}
