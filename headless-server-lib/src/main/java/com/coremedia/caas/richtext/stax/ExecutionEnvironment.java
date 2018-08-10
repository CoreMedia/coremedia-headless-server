package com.coremedia.caas.richtext.stax;

import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;
import com.coremedia.caas.richtext.stax.context.ParseContext;
import com.coremedia.caas.richtext.stax.handler.context.ContextHandler;
import com.coremedia.caas.richtext.stax.handler.context.ContextTracer;
import com.coremedia.caas.richtext.stax.handler.event.EventHandler;
import com.coremedia.caas.richtext.stax.handler.output.OutputHandler;
import com.coremedia.caas.richtext.stax.handler.output.OutputTracer;
import com.coremedia.caas.richtext.stax.writer.intermediate.IntermediateTree;
import com.coremedia.caas.richtext.stax.writer.intermediate.IntermediateTreeWriter;

import java.util.Map;
import java.util.Stack;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

public class ExecutionEnvironment {

  private IntermediateTree output;

  private Stack<ParseContext> contexts = new Stack<>();
  private Stack<EventHandler> handlers = new Stack<>();
  private Stack<ExecutionState> states = new Stack<>();


  public ExecutionEnvironment(StaxTransformationConfig config) {
    this.states.push(new ExecutionState(new IntermediateTreeWriter()));
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

  public void endDocument() {
    if (!handlers.isEmpty()) {
      throw new IllegalStateException("Handler stack not empty");
    }
    contexts.pop();
    if (!contexts.isEmpty()) {
      throw new IllegalStateException("Context stack not empty");
    }
    ExecutionState currentState = states.pop();
    if (!states.isEmpty()) {
      throw new IllegalStateException("Execution state stack not empty");
    }
    currentState.closeOutput();
    this.output = currentState.getOutput();
  }


  public IntermediateTree getOutput() {
    return this.output;
  }


  public IntermediateTreeWriter getWriter() {
    if (states.isEmpty()) {
      throw new IllegalStateException("Execution state stack is empty");
    }
    return states.peek().getWriter();
  }


  public void pushContext(ParseContext context) {
    contexts.push(context);
  }

  public void popContext() {
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


  public void setAttribute(String key, Object value) {
    ExecutionState currentState = states.peek();
    currentState.setAttribute(key, value);
  }

  public Object removeAttribute(String key, Object defaultValue) {
    ExecutionState currentState = states.peek();
    return currentState.removeAttribute(key, defaultValue);
  }

  public Object getAttribute(String key, Object defaultValue) {
    ExecutionState currentState = states.peek();
    return currentState.getAttribute(key, defaultValue);
  }


  public void pushState(Map<String, Object> initialAttributes) {
    ExecutionState currentState = states.peek();
    states.push(new ExecutionState(currentState, initialAttributes));
  }

  public void popState() {
    states.pop();
  }
}
