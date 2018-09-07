package com.coremedia.caas.generator.config;

import com.coremedia.cap.common.CapPropertyDescriptor;
import com.coremedia.cap.content.ContentType;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InterfaceTypeDefinition extends AbstractTypeDefinition {

  public InterfaceTypeDefinition(ContentType contentType, SchemaConfig schemaConfig) {
    super(contentType, schemaConfig);
  }


  @Override
  public InterfaceTypeDefinition getParent() {
    return getSchemaConfig().findParent(this);
  }

  @Override
  public String getName() {
    return super.getName(null);
  }

  @Override
  public List<String> getInterfaces() {
    ArrayList<String> result = new ArrayList<>();
    // add document type interface
    InterfaceTypeDefinition parent = getSchemaConfig().findParent(this);
    if (parent != null) {
      result.add(parent.getName());
    }
    // add custom interfaces
    TypeCustomization typeCustomization = getTypeCustomization();
    if (typeCustomization != null) {
      result.addAll(typeCustomization.getCustomInterfaces());
    }
    return result;
  }

  @Override
  public List<FieldDefinition> getFields() throws InvalidTypeDefinition {
    ArrayList<FieldDefinition> result = new ArrayList<>();
    TypeCustomization typeCustomization = getTypeCustomization();
    for (String name : getFieldNames()) {
      if (typeCustomization != null && typeCustomization.hasCustomField(name)) {
        result.add(typeCustomization.getCustomField(name));
      }
      else if (typeCustomization == null || !typeCustomization.getExcludedProperties().contains(name)) {
        result.add(new DocumentFieldDefinition(getSchemaConfig(), getContentType().getDescriptor(name)));
      }
    }
    result.sort(Comparator.comparing(FieldDefinition::getName));
    return result;
  }


  private boolean isValidPropertyDescriptor(CapPropertyDescriptor descriptor) {
    switch (descriptor.getType()) {
      case BLOB:
      case DATE:
      case INTEGER:
      case LINK:
      case MARKUP:
      case STRING:
        return true;
      default:
        return false;
    }
  }


  private Set<String> getParentFieldNames() throws InvalidTypeDefinition {
    InterfaceTypeDefinition parent = getParent();
    HashSet<String> result = new HashSet<>();
    while (parent != null) {
      result.addAll(parent.getFieldNames());
      parent = parent.getParent();
    }
    return result;
  }

  private Set<String> getFieldNames() throws InvalidTypeDefinition {
    Set<String> parentNames = getParentFieldNames();
    // get custom field names
    Set<String> customNames = new HashSet<>();
    TypeCustomization typeCustomization = getTypeCustomization();
    if (typeCustomization != null) {
      customNames = typeCustomization.getCustomFields().stream().map(FieldDefinition::getName).collect(Collectors.toSet());
    }
    for (String name : customNames) {
      // customizations may redefine local fields but must never change a parent field
      if (parentNames.contains(name)) {
        throw new InvalidTypeDefinition("Cannot override field " + name + " for type " + getName());
      }
    }
    Set<String> localNames = getContentType().getDescriptors().stream().filter(this::isValidPropertyDescriptor).map(CapPropertyDescriptor::getName).collect(Collectors.toSet());
    // remove all fields already defined by the parent
    localNames.removeAll(parentNames);
    // add custom fields
    localNames.addAll(customNames);
    return localNames;
  }


  @Override
  public void validate() throws InvalidTypeDefinition {
    // ensure interface fields are not redefined
    getFieldNames();
  }


  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("name", getName())
            .toString();
  }
}
