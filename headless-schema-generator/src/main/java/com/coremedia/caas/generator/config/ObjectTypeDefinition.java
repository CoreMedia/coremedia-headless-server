package com.coremedia.caas.generator.config;

import com.coremedia.cap.content.ContentType;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ObjectTypeDefinition extends AbstractTypeDefinition {

  private static final String OBJECTTYPE_SUFFIX = "Impl";


  public ObjectTypeDefinition(ContentType contentType, SchemaConfig schemaConfig) {
    super(contentType, schemaConfig);
  }


  @Override
  public String getName() {
    return super.getName(OBJECTTYPE_SUFFIX);
  }


  @Override
  public ObjectTypeDefinition getParent() {
    return getSchemaConfig().findParent(this);
  }


  @Override
  public List<InterfaceTypeDefinition> getInterfaceDefinitions() {
    return ImmutableList.of(getSchemaConfig().findInterface(this));
  }


  @Override
  public List<FieldDefinition> getFieldDefinitions() throws InvalidTypeDefinition {
    TypeCustomization typeCustomization = getTypeCustomization();
    ArrayList<FieldDefinition> result = Lists.newArrayList();
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
