package com.coremedia.caas.richtext.stax.writer;

import javax.xml.stream.XMLStreamWriter;

public interface XMLStreamWriterAdapter<E> extends XMLStreamWriter {

  E getOutput();
}
