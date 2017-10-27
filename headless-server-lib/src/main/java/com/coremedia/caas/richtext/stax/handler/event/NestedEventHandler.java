package com.coremedia.caas.richtext.stax.handler.event;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.google.common.collect.ImmutableMap;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import java.util.Map;

public class NestedEventHandler extends AbstractEventHandler {

  private Map<String, Object> initialAttributes = ImmutableMap.of();


  public Map<String, Object> getInitialAttributes() {
    return initialAttributes;
  }

  public void setInitialAttributes(Map<String, Object> initialAttributes) {
    this.initialAttributes = initialAttributes;
  }


  @Override
  public void startElement(StartElement startElement, ExecutionEnvironment<?> env) throws XMLStreamException {
    env.pushState(initialAttributes);
    super.startElement(startElement, env);
  }

  @Override
  public void endElement(EndElement endElement, ExecutionEnvironment<?> env) throws XMLStreamException {
    super.endElement(endElement, env);
    env.popState();
  }
}
