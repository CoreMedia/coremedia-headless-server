package com.coremedia.caas.richtext.stax.writer;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;

import javax.xml.stream.XMLStreamException;

public class StringWriterFactory implements XMLStreamWriterFactory<String> {

  @Override
  public XMLStreamWriterAdapter<String> createWriter(ExecutionEnvironment env) throws XMLStreamException {
    return new StringWriterAdapter(env);
  }
}
