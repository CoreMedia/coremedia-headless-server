package com.coremedia.caas.generator.config;

import com.coremedia.cap.content.ContentType;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ObjectTypeDefinition extends AbstractTypeDefinition {

  private static final String OBJECTTYPE_SUFFIX = "Impl";


  public ObjectTypeDefinition(ContentType contentType, SchemaConfig schemaConfig) {
    super(contentType, schemaConfig);
  }


  @Override
  public ObjectTypeDefinition getParent() {
    return getSchemaConfig().findParent(this);
  }

  @Override
  public String getName() {
    return super.getName(OBJECTTYPE_SUFFIX);
  }

  @Override
  public List<String> getInterfaces() {
    ArrayList<String> result = new ArrayList<>();
    // add document type interface
    InterfaceTypeDefinition baseInterface = getSchemaConfig().findInterface(this);
    if (baseInterface != null) {
      result.add(baseInterface.getName());
    }
    // add custom interfaces
    TypeCustomization typeCustomization = getTypeCustomization();
    if (typeCustomization != null) {
      result.addAll(typeCustomization.getCustomInterfaces());
    }
    return result;
  }

  @Override
  public List<FieldDefinition> getFields() {
    ArrayList<FieldDefinition> result = new ArrayList<>();
    TypeCustomization typeCustomization = getTypeCustomization();
    if (typeCustomization != null) {
      result.addAll(typeCustomization.getCustomFields());
    }
    result.sort(Comparator.comparing(FieldDefinition::getName));
    return result;
  }


  @Override
  public void validate() {
  }


  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("name", getName())
            .toString();
  }
}
