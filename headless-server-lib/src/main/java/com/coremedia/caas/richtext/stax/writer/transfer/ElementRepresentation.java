package com.coremedia.caas.richtext.stax.writer.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeName("Element")
public class ElementRepresentation extends AbstractRepresentation {

  @JsonProperty
  private String name;
  @JsonProperty
  private List<AttributeRepresentation> attributes;
  @JsonProperty
  private List<AbstractRepresentation> children;


  public ElementRepresentation(String name, List<AttributeRepresentation> attributes, List<AbstractRepresentation> children) {
    this.name = name;
    this.attributes = attributes;
    this.children = children;
  }
}
