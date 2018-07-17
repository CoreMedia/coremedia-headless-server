package com.coremedia.caas.richtext.output;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.writer.XMLStreamWriterAdapter;

import javax.xml.stream.XMLStreamException;

public interface OutputFactory<E> {

  XMLStreamWriterAdapter<E> createXMLWriter(ExecutionEnvironment<E> environment) throws XMLStreamException;
}
