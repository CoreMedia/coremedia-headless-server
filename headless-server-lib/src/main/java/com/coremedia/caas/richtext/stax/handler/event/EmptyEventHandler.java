package com.coremedia.caas.richtext.stax.handler.event;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

public class EmptyEventHandler implements EventHandler {

  private static final EmptyEventHandler SINGLETON_INSTANCE = new EmptyEventHandler();

  public static EmptyEventHandler instance() {
    return SINGLETON_INSTANCE;
  }


  @Override
  public boolean matches(StartElement startElement) {
    return false;
  }


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
  public void resolve(StaxTransformationConfig config) {
  }
}
