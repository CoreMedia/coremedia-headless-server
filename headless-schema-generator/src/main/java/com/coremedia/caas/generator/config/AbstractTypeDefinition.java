package com.coremedia.caas.generator.config;

import com.coremedia.cap.content.ContentType;

import com.google.common.base.Strings;

import java.util.Map;

public abstract class AbstractTypeDefinition implements TypeDefinition {

  private SchemaConfig schemaConfig;
  private ContentType contentType;


  public AbstractTypeDefinition(ContentType contentType, SchemaConfig schemaConfig) {
    this.contentType = contentType;
    this.schemaConfig = schemaConfig;
  }


  public abstract TypeDefinition getParent();

  @Override
  public Map<String, String> getOptions() {
    TypeCustomization customization = getTypeCustomization();
    if (customization != null) {
      return customization.getOptions();
    }
    return null;
  }


  protected String getName(String suffix) {
    StringBuilder builder = new StringBuilder();
    builder.append(getContentType().getName());
    if (!Strings.isNullOrEmpty(suffix)) {
      builder.append(suffix);
    }
    return builder.toString();
  }

  protected TypeCustomization getTypeCustomization() {
    return getSchemaConfig().findTypeCustomization(this);
  }

  protected ContentType getContentType() {
    return contentType;
  }

  protected SchemaConfig getSchemaConfig() {
    return schemaConfig;
  }
}
