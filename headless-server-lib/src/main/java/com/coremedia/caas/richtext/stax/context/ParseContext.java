package com.coremedia.caas.richtext.stax.context;

import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;
import com.coremedia.caas.richtext.stax.handler.event.EventHandler;

import javax.xml.stream.events.StartElement;

public interface ParseContext {

  String getName();

  EventHandler getHandler(StartElement startElement);

  <E> void resolveReferences(StaxTransformationConfig config);
}
