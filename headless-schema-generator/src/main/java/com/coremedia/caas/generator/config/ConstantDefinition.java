package com.coremedia.caas.generator.config;

public class ConstantDefinition implements FieldDefinition {

  public static final String TYPE_NAME = "Constant";


  private String name;
  private String targetType;
  private Object value;


  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getTargetType() {
    return targetType;
  }

  public void setTargetType(String targetType) {
    this.targetType = targetType;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }


  @Override
  public boolean isNonNull() {
    return true;
  }


  @Override
  public String getSourceName() {
    throw new IllegalArgumentException("Constant value cannot have a source");
  }

  @Override
  public String getTypeName() {
    return TYPE_NAME.toLowerCase();
  }
}
