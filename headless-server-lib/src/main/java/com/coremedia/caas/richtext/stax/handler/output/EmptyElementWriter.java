package com.coremedia.caas.richtext.stax.handler.output;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;

public class EmptyElementWriter extends AbstractOutputHandler {

  @Override
  public void startElement(StartElement startElement, ExecutionEnvironment env) throws XMLStreamException {
    env.getWriter().writeEmptyElement(startElement.getName().getLocalPart());
  }
}
