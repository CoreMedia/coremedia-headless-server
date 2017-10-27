package com.coremedia.caas.richtext.stax.handler.output;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

public abstract class AbstractOutputHandler implements OutputHandler {

  @Override
  public void startElement(StartElement startElement, ExecutionEnvironment env) throws XMLStreamException {
  }

  @Override
  public void endElement(EndElement endElement, ExecutionEnvironment env) throws XMLStreamException {
  }

  @Override
  public void characters(Characters characters, ExecutionEnvironment env) throws XMLStreamException {
  }


  @Override
  public OutputHandler decorate(ExecutionEnvironment env) {
    return env.decorate(this);
  }

  @Override
  public void resolveReferences(StaxTransformationConfig config) {
  }
}
