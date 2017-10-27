package com.coremedia.caas.generator.config;

import com.coremedia.cap.content.ContentType;
import com.google.common.base.Strings;

public abstract class AbstractTypeDefinition implements TypeDefinition {

  private SchemaConfig schemaConfig;
  private ContentType contentType;


  public AbstractTypeDefinition(ContentType contentType, SchemaConfig schemaConfig) {
    this.contentType = contentType;
    this.schemaConfig = schemaConfig;
  }


  protected ContentType getContentType() {
    return contentType;
  }

  protected SchemaConfig getSchemaConfig() {
    return schemaConfig;
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
}
