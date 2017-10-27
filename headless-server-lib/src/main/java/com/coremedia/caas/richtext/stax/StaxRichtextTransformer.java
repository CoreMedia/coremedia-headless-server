package com.coremedia.caas.richtext.stax;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.richtext.RichtextTransformer;
import com.coremedia.caas.richtext.common.RTElements;
import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;
import com.coremedia.xml.Markup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.StringReader;

public class StaxRichtextTransformer<E> implements RichtextTransformer<E> {

  private static final Logger LOG = LoggerFactory.getLogger(StaxRichtextTransformer.class);


  private StaxTransformationConfig<E> config;


  public StaxRichtextTransformer(StaxTransformationConfig<E> config) {
    this.config = config;
  }


  @Override
  public String getView() {
    return config.getName();
  }


  @Override
  public E transform(Markup markup, ExecutionContext executionContext) {
    try {
      return transformInternal(markup, executionContext);
    } catch (Throwable t) {
      LOG.error("markup parsing failed", t);
      return null;
    }
  }


  private E transformInternal(Markup markup, ExecutionContext executionContext) throws Exception {
    ExecutionEnvironment<E> env = new ExecutionEnvironment<E>(config, executionContext);

    XMLInputFactory inputFactory = env.getInputFactory();
    XMLEventReader eventReader = inputFactory.createXMLEventReader(new StringReader(markup.asXml()));

    while (eventReader.hasNext()) {
      XMLEvent event = eventReader.nextEvent();

      switch (event.getEventType()) {
        case XMLEvent.START_ELEMENT:
          StartElement startElement = event.asStartElement();
          if (startElement.getName().equals(RTElements.P.getQName())) {
            XMLEvent next = eventReader.peek();
            if (next.isStartElement() && next.asStartElement().getName().equals(RTElements.BR.getQName())) {
              // 'br' is an empty element, skip if directly after 'p'
              eventReader.nextEvent();
              eventReader.nextEvent();
            }
            // check if 'p' is closing
            next = eventReader.peek();
            if (next.isEndElement()) {
              // skip empty 'p'
              eventReader.nextEvent();
              continue;
            }
          }
          env.handle(startElement);
          break;

        case XMLEvent.END_ELEMENT:
          EndElement endElement = event.asEndElement();
          env.handle(endElement);
          break;

        case XMLEvent.CHARACTERS:
          Characters characters = event.asCharacters();
          env.handle(characters);
          break;

        case XMLEvent.START_DOCUMENT:
          env.startDocument();
          break;

        case XMLEvent.END_DOCUMENT:
          env.endDocument();
          break;
      }
    }
    return env.getOutput();
  }
}
