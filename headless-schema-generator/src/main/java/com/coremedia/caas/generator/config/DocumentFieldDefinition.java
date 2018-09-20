package com.coremedia.caas.generator.config;

import com.coremedia.cap.common.CapPropertyDescriptor;
import com.coremedia.cap.common.XmlGrammar;
import com.coremedia.cap.common.descriptors.LinkPropertyDescriptor;
import com.coremedia.cap.common.descriptors.MarkupPropertyDescriptor;

import java.util.Collections;
import java.util.List;

public class DocumentFieldDefinition implements FieldDefinition {

  private static final String COREMEDIA_RICHTEXT = "coremedia-richtext-1.0";


  private CapPropertyDescriptor propertyDescriptor;
  private SchemaConfig schema;


  public DocumentFieldDefinition(SchemaConfig schema, CapPropertyDescriptor propertyDescriptor) {
    this.schema = schema;
    this.propertyDescriptor = propertyDescriptor;
  }


  @Override
  public boolean isNonNull() {
    return false;
  }


  @Override
  public String getName() {
    return propertyDescriptor.getName();
  }

  @Override
  public String getSourceName() {
    return getName();
  }

  @Override
  public List<String> getFallbackSourceNames() {
    return Collections.emptyList();
  }

  @Override
  public List<DirectiveDefinition> getDirectives() {
    return Collections.emptyList();
  }


  @Override
  public String getTargetType() {
    switch (propertyDescriptor.getType()) {
      case DATE:
        return "String";
      case INTEGER:
        return "Int";
      case LINK:
        LinkPropertyDescriptor linkPropertyDescriptor = (LinkPropertyDescriptor) propertyDescriptor;
        InterfaceTypeDefinition targetType = schema.findInterface(linkPropertyDescriptor.getLinkType());
        if (targetType != null) {
          if (linkPropertyDescriptor.getMaxCardinality() == 1) {
            return targetType.getName();
          }
          else {
            return "List:" + targetType.getName();
          }
        }
        break;
      default:
        return getTypeName().substring(0, 1).toUpperCase() + getTypeName().substring(1).toLowerCase();
    }
    return null;
  }


  @Override
  public String getTypeName() {
    switch (propertyDescriptor.getType()) {
      case LINK:
        if (propertyDescriptor.getMaxCardinality() == 1) {
          return "link";
        }
        else {
          return "linklist";
        }
      case MARKUP:
        MarkupPropertyDescriptor markupPropertyDescriptor = (MarkupPropertyDescriptor) propertyDescriptor;
        XmlGrammar grammar = markupPropertyDescriptor.getGrammar();
        if (COREMEDIA_RICHTEXT.equals(grammar.getName())) {
          return "richtext";
        }
      default:
        return propertyDescriptor.getType().toString().toLowerCase();
    }
  }
}
