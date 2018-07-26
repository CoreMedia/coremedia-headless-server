package com.coremedia.caas.richtext.stax.writer.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Attribute")
public class AttributeRepresentation extends AbstractRepresentation {

  @JsonProperty
  private String name;
  @JsonProperty
  private String value;


  public AttributeRepresentation(String name, String value) {
    this.name = name;
    this.value = value;
  }
}
