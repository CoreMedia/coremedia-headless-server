package com.coremedia.caas.richtext.stax.transformer.attribute;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

public interface AttributeTransformer {

  Attribute getAttribute(StartElement startElement, ExecutionEnvironment env);
}
