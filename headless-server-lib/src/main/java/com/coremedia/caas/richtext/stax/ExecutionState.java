package com.coremedia.caas.richtext.stax;

import com.coremedia.caas.richtext.stax.writer.intermediate.IntermediateTree;
import com.coremedia.caas.richtext.stax.writer.intermediate.IntermediateTreeWriter;

import java.util.HashMap;
import java.util.Map;

public class ExecutionState {

  private ExecutionState parent;
  private IntermediateTreeWriter writer;

  private Map<String, Object> attributes;


  public ExecutionState(IntermediateTreeWriter writer) {
    this.writer = writer;
    this.attributes = new HashMap<>();
  }

  public ExecutionState(ExecutionState parent, Map<String, Object> initialAttributes) {
    this.parent = parent;
    if (initialAttributes == null || initialAttributes.isEmpty()) {
      this.attributes = new HashMap<>();
    }
    else {
      this.attributes = new HashMap<>(initialAttributes);
    }
  }


  public IntermediateTreeWriter getWriter() {
    if (writer != null) {
      return writer;
    }
    if (parent != null) {
      return parent.getWriter();
    }
    throw new IllegalStateException("Root state has no writer");
  }


  public IntermediateTree getOutput() {
    if (writer != null) {
      return writer.getOutput();
    }
    if (parent != null) {
      return parent.getOutput();
    }
    return null;
  }


  public void closeOutput() {
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
