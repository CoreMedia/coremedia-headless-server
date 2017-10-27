package com.coremedia.caas.richtext.stax.handler.context;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;

import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

public interface ContextHandler {

  void startElement(StartElement startElement, ExecutionEnvironment env);

  void endElement(EndElement endElement, ExecutionEnvironment env);


  ContextHandler decorate(ExecutionEnvironment env);


  void resolveReferences(StaxTransformationConfig config);
}
