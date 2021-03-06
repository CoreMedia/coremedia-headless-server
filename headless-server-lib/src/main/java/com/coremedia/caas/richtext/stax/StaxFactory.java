package com.coremedia.caas.richtext.stax;

import java.io.StringReader;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;

public class StaxFactory {

  private static XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
  private static XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();


  public static synchronized XMLEventReader createXMLEventReader(StringReader reader) throws XMLStreamException {
    return xmlInputFactory.createXMLEventReader(reader);
  }

  public static synchronized Attribute createAttribute(QName qName, String value) {
    return xmlEventFactory.createAttribute(qName, value);
  }
}
