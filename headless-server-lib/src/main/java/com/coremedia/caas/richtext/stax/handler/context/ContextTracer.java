package com.coremedia.caas.richtext.stax.handler.context;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

public class ContextTracer implements ContextHandler {

  private static final Logger LOG = LoggerFactory.getLogger(ContextTracer.class);


  private ContextHandler delegate;


  public ContextTracer(ContextHandler delegate) {
    this.delegate = delegate;
  }


  @Override
  public void startElement(StartElement startElement, ExecutionEnvironment env) {
    LOG.trace("startElement: {}, {}", delegate, startElement);
    delegate.startElement(startElement, env);
  }

  @Override
  public void endElement(EndElement endElement, ExecutionEnvironment env) {
    LOG.trace("endElement: {}, {}", delegate, endElement);
    delegate.endElement(endElement, env);
  }


  @Override
  public ContextHandler decorate(ExecutionEnvironment env) {
    throw new IllegalStateException("Cannot decorate decorator");
  }


  @Override
  public void resolveReferences(StaxTransformationConfig config) {
  }
}
