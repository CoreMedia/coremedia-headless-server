package com.coremedia.caas.richtext.stax.handler.output;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

public class OutputTracer implements OutputHandler {

  private static final Logger LOG = LoggerFactory.getLogger(OutputTracer.class);


  private OutputHandler delegate;


  public OutputTracer(OutputHandler delegate) {
    this.delegate = delegate;
  }


  @Override
  public void startElement(StartElement startElement, ExecutionEnvironment env) throws XMLStreamException {
    LOG.trace("startElement: {}, {}", delegate, startElement);
    delegate.startElement(startElement, env);
  }

  @Override
  public void endElement(EndElement endElement, ExecutionEnvironment env) throws XMLStreamException {
    LOG.trace("endElement: {}, {}", delegate, endElement);
    delegate.endElement(endElement, env);
  }

  @Override
  public void characters(Characters characters, ExecutionEnvironment env) throws XMLStreamException {
    LOG.trace("characters: {}, {}", delegate, characters);
    delegate.characters(characters, env);
  }


  @Override
  public OutputHandler decorate(ExecutionEnvironment env) {
    throw new IllegalStateException("Cannot decorate decorator");
  }

  @Override
  public void resolveReferences(StaxTransformationConfig config) {
  }
}
