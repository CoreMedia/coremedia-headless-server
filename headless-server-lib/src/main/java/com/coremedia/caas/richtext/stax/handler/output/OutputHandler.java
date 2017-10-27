package com.coremedia.caas.richtext.stax.handler.output;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

public interface OutputHandler {

  void startElement(StartElement startElement, ExecutionEnvironment env) throws XMLStreamException;

  void endElement(EndElement endElement, ExecutionEnvironment env) throws XMLStreamException;

  void characters(Characters characters, ExecutionEnvironment env) throws XMLStreamException;


  OutputHandler decorate(ExecutionEnvironment env);

  void resolveReferences(StaxTransformationConfig config);
}
