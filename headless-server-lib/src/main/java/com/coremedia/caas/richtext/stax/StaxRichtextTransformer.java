package com.coremedia.caas.richtext.stax;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.richtext.RichtextTransformer;
import com.coremedia.caas.richtext.common.RTElements;
import com.coremedia.caas.richtext.output.OutputFactory;
import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;
import com.coremedia.xml.Markup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StaxRichtextTransformer implements RichtextTransformer {

  private static final Logger LOG = LoggerFactory.getLogger(StaxRichtextTransformer.class);


  private StaxTransformationConfig config;


  public StaxRichtextTransformer(StaxTransformationConfig config) {
    this.config = config;
  }


  @Override
  public String getView() {
    return config.getName();
  }


  @Override
  public <E> E transform(Markup markup, OutputFactory<E> outputFactory, ExecutionContext executionContext) {
    try {
      return transformInternal(markup, outputFactory, executionContext);
    } catch (Throwable t) {
      LOG.error("markup parsing failed", t);
      return null;
    }
  }


  private <E> E transformInternal(Markup markup, OutputFactory<E> outputFactory, ExecutionContext executionContext) throws Exception {
    ExecutionEnvironment<E> env = new ExecutionEnvironment<>(config, outputFactory, executionContext);

    XMLEventReader eventReader = StaxFactory.createXMLEventReader(new StringReader(markup.asXml()));
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
