package com.coremedia.caas.richtext.stax.writer.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Characters")
public class CharactersRepresentation extends AbstractRepresentation {

  @JsonProperty
  private String data;


  public CharactersRepresentation(String data) {
    this.data = data;
  }
}
