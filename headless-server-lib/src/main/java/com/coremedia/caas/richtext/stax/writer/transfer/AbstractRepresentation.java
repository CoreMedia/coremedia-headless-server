package com.coremedia.caas.richtext.stax.writer.transfer;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "_type")
public class AbstractRepresentation {
}
