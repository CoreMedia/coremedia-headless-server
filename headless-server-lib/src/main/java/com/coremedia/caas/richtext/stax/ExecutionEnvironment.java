package com.coremedia.caas.richtext.stax;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.link.LinkBuilder;
import com.coremedia.caas.richtext.output.OutputFactory;
import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;
import com.coremedia.caas.richtext.stax.context.ParseContext;
import com.coremedia.caas.richtext.stax.handler.context.ContextHandler;
import com.coremedia.caas.richtext.stax.handler.context.ContextTracer;
import com.coremedia.caas.richtext.stax.handler.event.EventHandler;
import com.coremedia.caas.richtext.stax.handler.output.OutputHandler;
import com.coremedia.caas.richtext.stax.handler.output.OutputTracer;
import com.coremedia.caas.service.repository.ProxyFactory;
import com.coremedia.caas.service.repository.RootContext;

import java.util.Map;
import java.util.Stack;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

public class ExecutionEnvironment<E> {

  private OutputFactory<E> outputFactory;
  private ExecutionContext executionContext;

  private E output;

  private Stack<ParseContext> contexts = new Stack<>();
  private Stack<EventHandler> handlers = new Stack<>();
  private Stack<ExecutionState<E>> states = new Stack<>();


  public ExecutionEnvironment(StaxTransformationConfig config, OutputFactory<E> outputFactory, ExecutionContext executionContext) throws XMLStreamException {
    this.outputFactory = outputFactory;
    this.executionContext = executionContext;
    this.states.push(new ExecutionState<>(outputFactory.createXMLWriter(this)));
    this.contexts.push(config.getInitialContext());
  }


  private boolean isTraceEnabled() {
    return false;
  }


  public void handle(StartElement startElement) throws XMLStreamException {
    ParseContext currentContext = contexts.peek();
    EventHandler handler = currentContext.getHandler(startElement);
    handlers.push(handler);
    handler.startElement(startElement, this);
  }

  public void handle(EndElement endElement) throws XMLStreamException {
    EventHandler handler = handlers.pop();
    handler.endElement(endElement, this);
  }

  public void handle(Characters characters) throws XMLStreamException {
    EventHandler handler = handlers.peek();
    handler.characters(characters, this);
  }


  public void startDocument() {
  }

  public void endDocument() throws XMLStreamException {
    if (!handlers.isEmpty()) {
      throw new IllegalStateException("Handler stack not empty");
    }
    ParseContext currentContext = contexts.pop();
    if (!contexts.isEmpty()) {
      throw new IllegalStateException("Context stack not empty");
    }
    ExecutionState<E> currentState = states.pop();
    if (!states.isEmpty()) {
      throw new IllegalStateException("Execution state stack not empty");
    }
    currentState.closeOutput();
    this.output = currentState.getOutput();
  }


  public E getOutput() {
    return this.output;
  }


  public XMLStreamWriter getWriter() {
    if (states.isEmpty()) {
      throw new IllegalStateException("Execution state stack is empty");
    }
    return states.peek().getWriter();
  }


  public void pushContext(ParseContext context) {
    contexts.push(context);
  }

  public void popContext(ParseContext context) {
    contexts.pop();
  }

  public void replaceContext(ParseContext context) {
    contexts.pop();
    contexts.push(context);
  }


  public ContextHandler decorate(ContextHandler action) {
    if (isTraceEnabled()) {
      return new ContextTracer(action);
    }
    return action;
  }

  public OutputHandler decorate(OutputHandler action) {
    if (isTraceEnabled()) {
      return new OutputTracer(action);
    }
    return action;
  }


  public LinkBuilder getLinkBuilder() {
    return executionContext.getProcessingDefinition().getLinkBuilder();
  }

  public RootContext getRootContext() {
    return executionContext.getRootContext();
  }

  public ProxyFactory getProxyFactory() {
    return getRootContext().getProxyFactory();
  }


  public void setAttribute(String key, Object value) {
    ExecutionState<E> currentState = states.peek();
    currentState.setAttribute(key, value);
  }

  public Object removeAttribute(String key, Object defaultValue) {
    ExecutionState<E> currentState = states.peek();
    return currentState.removeAttribute(key, defaultValue);
  }

  public Object getAttribute(String key, Object defaultValue) {
    ExecutionState<E> currentState = states.peek();
    return currentState.getAttribute(key, defaultValue);
  }


  public void pushState(Map<String, Object> initialAttributes) {
    ExecutionState<E> currentState = states.peek();
    states.push(new ExecutionState<E>(currentState, initialAttributes));
  }

  public void popState() {
    states.pop();
  }
}
