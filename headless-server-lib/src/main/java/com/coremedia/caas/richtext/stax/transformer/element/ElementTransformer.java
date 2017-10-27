package com.coremedia.caas.richtext.stax.transformer.element;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;

import javax.xml.namespace.QName;
import javax.xml.stream.events.StartElement;

public interface ElementTransformer {

  QName getName(StartElement startElement, ExecutionEnvironment env);
}
