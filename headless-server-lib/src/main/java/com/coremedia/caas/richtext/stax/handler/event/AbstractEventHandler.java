package com.coremedia.caas.richtext.stax.handler.event;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;
import com.coremedia.caas.richtext.stax.handler.context.ContextHandler;
import com.coremedia.caas.richtext.stax.handler.matcher.Matcher;
import com.coremedia.caas.richtext.stax.handler.output.OutputHandler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

public abstract class AbstractEventHandler implements EventHandler {

  private Matcher eventMatcher;
  private OutputHandler outputHandler;
  private ContextHandler contextHandler;


  public Matcher getEventMatcher() {
    return eventMatcher;
  }

  public void setEventMatcher(Matcher eventMatcher) {
    this.eventMatcher = eventMatcher;
  }

  public OutputHandler getOutputHandler() {
    return outputHandler;
  }

  public void setOutputHandler(OutputHandler outputHandler) {
    this.outputHandler = outputHandler;
  }

  public ContextHandler getContextHandler() {
    return contextHandler;
  }

  public void setContextHandler(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }


  @Override
  public boolean matches(StartElement startElement) {
    if (eventMatcher != null) {
      return eventMatcher.matches(startElement);
    }
    return false;
  }


  @Override
  public void startElement(StartElement startElement, ExecutionEnvironment env) throws XMLStreamException {
    if (contextHandler != null) {
      contextHandler.decorate(env).startElement(startElement, env);
    }
    if (outputHandler != null) {
      outputHandler.decorate(env).startElement(startElement, env);
    }
  }

  @Override
  public void endElement(EndElement endElement, ExecutionEnvironment env) throws XMLStreamException {
    if (outputHandler != null) {
      outputHandler.decorate(env).endElement(endElement, env);
    }
    if (contextHandler != null) {
      contextHandler.decorate(env).endElement(endElement, env);
    }
  }

  @Override
  public void characters(Characters characters, ExecutionEnvironment env) throws XMLStreamException {
    if (outputHandler != null) {
      outputHandler.decorate(env).characters(characters, env);
    }
  }


  @Override
  public void resolve(StaxTransformationConfig config) {
    if (contextHandler != null) {
      contextHandler.resolveReferences(config);
    }
    if (outputHandler != null) {
      outputHandler.resolveReferences(config);
    }
  }
}
