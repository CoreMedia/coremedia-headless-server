package com.coremedia.caas.richtext.stax.writer;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;

import javax.xml.stream.XMLStreamException;

public interface XMLStreamWriterFactory<E> {

  XMLStreamWriterAdapter<E> createWriter(ExecutionEnvironment env) throws XMLStreamException;
}
