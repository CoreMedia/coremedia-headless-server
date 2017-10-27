package com.coremedia.caas.richtext.stax;

import com.coremedia.caas.richtext.stax.writer.XMLStreamWriterAdapter;

import javax.xml.stream.XMLStreamException;
import java.util.HashMap;
import java.util.Map;

public class ExecutionState<E> {

  private ExecutionState<E> parent;
  private XMLStreamWriterAdapter<E> writer;

  private Map<String, Object> attributes;


  public ExecutionState(XMLStreamWriterAdapter<E> writer) {
    this.writer = writer;
    this.attributes = new HashMap<>();
  }

  public ExecutionState(ExecutionState<E> parent, Map<String, Object> initialAttributes) {
    this.parent = parent;
    if (initialAttributes == null || initialAttributes.isEmpty()) {
      this.attributes = new HashMap<>();
    } else {
      this.attributes = new HashMap<>(initialAttributes);
    }
  }


  public XMLStreamWriterAdapter<E> getWriter() {
    if (writer != null) {
      return writer;
    }
    if (parent != null) {
      return parent.getWriter();
    }
    throw new IllegalStateException("Root state has no writer");
  }


  public E getOutput() {
    if (writer != null) {
      return writer.getOutput();
    }
    if (parent != null) {
      return parent.getOutput();
    }
    return null;
  }


  public void closeOutput() throws XMLStreamException {
    if (writer != null) {
      writer.flush();
      writer.close();
    }
  }


  public Object getAttribute(String key, Object defaultValue) {
    Object value = attributes.get(key);
    if (value != null) {
      return value;
    }
    return defaultValue;
  }

  public void setAttribute(String key, Object value) {
    attributes.put(key, value);
  }

  public Object removeAttribute(String key, Object defaultValue) {
    Object value = attributes.remove(key);
    if (value != null) {
      return value;
    }
    return defaultValue;
  }
}
