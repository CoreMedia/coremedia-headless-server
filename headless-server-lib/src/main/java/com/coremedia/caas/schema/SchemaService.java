package com.coremedia.caas.schema;

import com.coremedia.caas.schema.directive.CustomDirective;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.content.ContentType;

import com.google.common.collect.HashMultimap;
import graphql.schema.GraphQLDirective;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLType;
import graphql.schema.TypeResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SchemaService {

  private static final String CONTENT_INTERFACE_TYPE_SUFFIX = "";
  private static final String CONTENT_OBJECT_TYPE_SUFFIX = "Impl";


  private Map<String, CustomDirective> customDirectives;
  private Map<String, TypeDefinition> typeDefinitions;


  private TypeResolver typeResolver;

  private Set<GraphQLDirective> directives;
  private Set<GraphQLType> types;

  private Map<String, GraphQLDirective> directiveMapping;
  private Map<String, GraphQLType> typeMapping;

  private Map<ContentType, GraphQLInterfaceType> contentTypeInterfaceMapping = new HashMap<>();
  private Map<ContentType, GraphQLObjectType> contentTypeObjectMapping = new HashMap<>();

  private HashMultimap<ContentType, String> contentTypeInstanceNamesMapping;


  public SchemaService(Set<CustomDirective> customDirectives, Set<TypeDefinition> typeDefinitions, ContentRepository contentRepository) {
    this.customDirectives = customDirectives.stream().collect(Collectors.toMap(CustomDirective::getName, Function.identity()));
    this.typeDefinitions = typeDefinitions.stream().collect(Collectors.toMap(TypeDefinition::getName, Function.identity()));
    // create type resolver
    this.typeResolver = new ObjectTypeResolver(this);
    // build GraphQL types
    buildTypes(contentRepository);
  }


  /*
   * Directive definition operations
   */

  public CustomDirective getCustomDirective(String name) {
    return customDirectives.get(name);
  }


  /*
   * Type definition operations
   */

  public InterfaceType getInterfaceTypeDefinition(String name) {
    return (InterfaceType) typeDefinitions.get(name);
  }

  public ObjectType getObjectTypeDefinition(String name) {
    return (ObjectType) typeDefinitions.get(name);
  }


  /*
   * GraphQL type operations
   */

  public boolean isInstanceOf(Object source, String name) {
    if (source instanceof Content) {
      Set<String> instanceNames = contentTypeInstanceNamesMapping.get(((Content) source).getType());
      if (instanceNames != null) {
        return instanceNames.contains(name);
      }
    }
    return false;
  }


  public boolean hasDirective(String name) {
    return directiveMapping.containsKey(name);
  }

  public boolean hasType(String name) {
    return typeMapping.containsKey(name);
  }


  public Set<GraphQLDirective> getDirectives() {
    return directives;
  }

  public Set<GraphQLType> getTypes() {
    return types;
  }


  public GraphQLInterfaceType getInterfaceType(Object source) {
    if (source instanceof Content) {
      return contentTypeInterfaceMapping.get(((Content) source).getType());
    }
    return null;
  }

  public GraphQLObjectType getObjectType(Object source) {
    if (source instanceof Content) {
      return contentTypeObjectMapping.get(((Content) source).getType());
    }
    return null;
  }


  public TypeResolver getTypeResolver() {
    return typeResolver;
  }


  /*
   * Internal GraphQL type building and mapping
   */

  private void buildTypes(ContentRepository contentRepository) {
    // map all directives
    directives = customDirectives.values().stream().map(CustomDirective::createDirective).collect(Collectors.toSet());
    directiveMapping = directives.stream().collect(Collectors.toMap(GraphQLDirective::getName, Function.identity()));
    // map all types
    types = typeDefinitions.values().stream().map(e -> e.build(this)).collect(Collectors.toSet());
    typeMapping = types.stream().collect(Collectors.toMap(GraphQLType::getName, Function.identity()));
    // map type names to object and interface types
    Map<String, GraphQLInterfaceType> interfaceTypes = typeMapping.values().stream().filter(GraphQLInterfaceType.class::isInstance).map(GraphQLInterfaceType.class::cast).collect(Collectors.toMap(GraphQLInterfaceType::getName, Function.identity()));
    Map<String, GraphQLObjectType> objectTypes = typeMapping.values().stream().filter(GraphQLObjectType.class::isInstance).map(GraphQLObjectType.class::cast).collect(Collectors.toMap(GraphQLObjectType::getName, Function.identity()));
    // build content type mapping to object and interface types
    for (ContentType contentType : contentRepository.getContentTypes()) {
      buildContentTypeMapping(contentType, CONTENT_INTERFACE_TYPE_SUFFIX, interfaceTypes, contentTypeInterfaceMapping);
      buildContentTypeMapping(contentType, CONTENT_OBJECT_TYPE_SUFFIX, objectTypes, contentTypeObjectMapping);
    }
    // build content type mapping to all implemented object and interface type names
    contentTypeInstanceNamesMapping = HashMultimap.create();
    for (ContentType contentType : contentRepository.getContentTypes()) {
      ContentType currentContentType = contentType;
      while (currentContentType != null) {
        GraphQLObjectType type = contentTypeObjectMapping.get(currentContentType);
        // add object type name
        contentTypeInstanceNamesMapping.put(contentType, type.getName());
        // add all implemented interface names
        contentTypeInstanceNamesMapping.putAll(contentType, type.getInterfaces().stream().map(GraphQLOutputType::getName).collect(Collectors.toList()));
        currentContentType = currentContentType.getParent();
      }
    }
  }


  private <T> void buildContentTypeMapping(ContentType contentType, String suffix, Map<String, T> definedTypes, Map<ContentType, T> typeMapping) {
    ContentType currentContentType = contentType;
    while (currentContentType != null) {
      T type = definedTypes.get(currentContentType.getName() + suffix);
      if (type != null) {
        typeMapping.put(contentType, type);
        return;
      }
      currentContentType = currentContentType.getParent();
    }
    throw new InvalidTypeDefinition("Content type must be resolved: " + contentType);
  }
}
