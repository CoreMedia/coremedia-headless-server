package com.coremedia.caas.richtext.stax.writer.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeName("EmptyElement")
public class EmptyElementRepresentation extends AbstractRepresentation {

  @JsonProperty
  private String name;
  @JsonProperty
  private List<AttributeRepresentation> attributes;


  public EmptyElementRepresentation(String name, List<AttributeRepresentation> attributes) {
    this.name = name;
    this.attributes = attributes;
  }
}
