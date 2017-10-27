package com.coremedia.caas.generator.config;

import com.coremedia.cap.common.CapType;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.content.ContentType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SchemaConfig {

  private ContentRepository contentRepository;


  private List<String> includedTypes;
  private List<String> excludedTypes;

  private Map<String, TypeCustomization> typeCustomizations = ImmutableMap.of();

  private Map<String, InterfaceTypeDefinition> interfaceTypeDefinitions = Maps.newHashMap();
  private Map<String, ObjectTypeDefinition> objectTypeDefinitions = Maps.newHashMap();


  void setContentRepository(ContentRepository contentRepository) {
    this.contentRepository = contentRepository;
  }

  void setTypeCustomizations(List<TypeCustomization> typeCustomizations) {
    this.typeCustomizations = typeCustomizations.stream().collect(Collectors.toMap(TypeCustomization::getName, e -> e));
  }


  public List<String> getIncludedTypes() {
    return includedTypes;
  }

  public void setIncludedTypes(List<String> includedTypes) {
    this.includedTypes = includedTypes;
  }

  public List<String> getExcludedTypes() {
    return excludedTypes;
  }

  public void setExcludedTypes(List<String> excludedTypes) {
    this.excludedTypes = excludedTypes;
  }

  public List<TypeCustomization> getTypeCustomizations() {
    return ImmutableList.copyOf(typeCustomizations.values());
  }


  public Collection<InterfaceTypeDefinition> getInterfaceTypeDefinitions() {
    return interfaceTypeDefinitions.values();
  }

  public Collection<ObjectTypeDefinition> getObjectTypeDefinitions() {
    return objectTypeDefinitions.values();
  }


  public TypeCustomization findTypeCustomization(TypeDefinition type) {
    return typeCustomizations.get(type.getName());
  }

  public InterfaceTypeDefinition findParent(InterfaceTypeDefinition child) {
    return findInterface(child.getContentType().getParent());
  }

  public ObjectTypeDefinition findParent(ObjectTypeDefinition child) {
    ContentType parent = child.getContentType().getParent();
    while (parent != null) {
      ObjectTypeDefinition definition = objectTypeDefinitions.get(parent.getName());
      if (definition != null) {
        return definition;
      }
      parent = parent.getParent();
    }
    return null;
  }

  public InterfaceTypeDefinition findInterface(ObjectTypeDefinition objectType) {
    return interfaceTypeDefinitions.get(objectType.getContentType().getName());
  }

  public InterfaceTypeDefinition findInterface(CapType type) {
    CapType parent = type;
    while (parent != null) {
      InterfaceTypeDefinition definition = interfaceTypeDefinitions.get(parent.getName());
      if (definition != null) {
        return definition;
      }
      parent = parent.getParent();
    }
    return null;
  }


  private InterfaceTypeDefinition createDocumentInterfaceTypeDefinition(ContentType contentType) {
    return new InterfaceTypeDefinition(contentType, this);
  }

  private ObjectTypeDefinition createDocumentObjectTypeDefinition(ContentType contentType) {
    return new ObjectTypeDefinition(contentType, this);
  }


  private void initDocumentTypeDefinitions() {
    List<ContentType> contentTypes = contentRepository.getContentTypes()
            .stream()
            .filter(e -> (includedTypes == null || includedTypes.isEmpty()) || includedTypes.contains(e.getName()))
            .filter(e -> (excludedTypes == null || excludedTypes.isEmpty()) || !excludedTypes.contains(e.getName()))
            .collect(Collectors.toList());

    interfaceTypeDefinitions.putAll(contentTypes.stream().map(this::createDocumentInterfaceTypeDefinition).collect(Collectors.toMap(e -> e.getContentType().getName(), e -> e)));
    objectTypeDefinitions.putAll(contentTypes.stream().map(this::createDocumentObjectTypeDefinition).collect(Collectors.toMap(e -> e.getContentType().getName(), e -> e)));
  }


  public void init() throws InvalidTypeDefinition {
    initDocumentTypeDefinitions();
    validate();
  }


  protected void validate() throws InvalidTypeDefinition {
    // check if only included or excluded is used
    if (includedTypes != null && excludedTypes != null && !(includedTypes.isEmpty() || excludedTypes.isEmpty())) {
      throw new IllegalArgumentException("cannot use both included and excluded types");
    }
    // check if all included types exist
    if (includedTypes != null && !includedTypes.isEmpty()) {
      for (String includedType : includedTypes) {
        if (contentRepository.getContentType(includedType) == null) {
          throw new IllegalArgumentException(includedType);
        }
      }
    }
    for (TypeDefinition typeDefinition : getInterfaceTypeDefinitions()) {
      typeDefinition.validate();
    }
    for (TypeDefinition typeDefinition : getObjectTypeDefinitions()) {
      typeDefinition.validate();
    }
  }
}
