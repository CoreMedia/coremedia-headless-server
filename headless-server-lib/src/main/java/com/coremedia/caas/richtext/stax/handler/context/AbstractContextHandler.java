package com.coremedia.caas.richtext.stax.handler.context;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;
import com.coremedia.caas.richtext.stax.context.ParseContext;

import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

public abstract class AbstractContextHandler implements ContextHandler {

  @Override
  public void startElement(StartElement startElement, ExecutionEnvironment env) {
  }

  @Override
  public void endElement(EndElement endElement, ExecutionEnvironment env) {
  }


  @Override
  public ContextHandler decorate(ExecutionEnvironment env) {
    return env.decorate(this);
  }


  @Override
  public void resolveReferences(StaxTransformationConfig config) {
  }


  protected ParseContext resolveContextReference(StaxTransformationConfig config, String name) {
    ParseContext context = config.getContext(name);
    if (context == null) {
      throw new IllegalStateException("Invalid referenced context: " + name);
    }
    return context;
  }
}
