package com.coremedia.caas.richtext.stax.config;

import com.coremedia.caas.richtext.stax.context.ParseContext;
import com.coremedia.caas.richtext.stax.handler.event.EventHandler;
import com.coremedia.caas.richtext.stax.writer.XMLStreamWriterFactory;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StaxTransformationConfig<E> {

  private String name;
  private List<QName> elements;
  private List<String> classes;
  private ParseContext initialContext;
  private List<ParseContext> contexts;
  private Map<String, ParseContext> contextMapping;
  private Map<String, List<EventHandler>> handlerSets;
  private XMLStreamWriterFactory<E> writerFactory;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<QName> getElements() {
    return elements;
  }

  public void setElements(List<QName> elements) {
    this.elements = elements;
  }

  public List<String> getClasses() {
    return classes;
  }

  public void setClasses(List<String> classes) {
    this.classes = classes;
  }

  public ParseContext getInitialContext() {
    return initialContext;
  }

  public void setInitialContext(ParseContext initialContext) {
    this.initialContext = initialContext;
  }

  public Map<String, ParseContext> getContextMapping() {
    return contextMapping;
  }

  public ParseContext getContext(String name) {
    return contextMapping.get(name);
  }

  public List<ParseContext> getContexts() {
    return contexts;
  }

  public void setContexts(List<ParseContext> contexts) {
    this.contexts = contexts;
    this.contextMapping = this.contexts.stream().collect(Collectors.toMap(ParseContext::getName, Function.identity()));
  }

  public Map<String, List<EventHandler>> getHandlerSets() {
    return handlerSets;
  }

  public void setHandlerSets(Map<String, List<EventHandler>> handlerSets) {
    this.handlerSets = handlerSets;
  }

  public XMLStreamWriterFactory<E> getWriterFactory() {
    return writerFactory;
  }

  public void setWriterFactory(XMLStreamWriterFactory<E> writerFactory) {
    this.writerFactory = writerFactory;
  }


  public StaxTransformationConfig resolve() {
    for (ParseContext context : getContexts()) {
      context.resolveReferences(this);
    }
    return this;
  }
}
