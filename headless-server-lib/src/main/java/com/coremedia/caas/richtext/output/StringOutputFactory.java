package com.coremedia.caas.richtext.output;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.writer.StringWriterAdapter;
import com.coremedia.caas.richtext.stax.writer.XMLStreamWriterAdapter;

import javax.xml.stream.XMLStreamException;

public class StringOutputFactory implements OutputFactory<String> {

  @Override
  public XMLStreamWriterAdapter<String> createXMLWriter(ExecutionEnvironment<String> environment) throws XMLStreamException {
    return new StringWriterAdapter(environment);
  }
}
