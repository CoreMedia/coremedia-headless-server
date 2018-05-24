package com.coremedia.caas.richtext.stax.handler.output;

import com.coremedia.caas.richtext.common.RTAttributes;
import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.services.repository.content.ContentProxy;
import com.coremedia.cap.common.IdHelper;

import com.google.common.base.Strings;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

public class LinkWriter extends AbstractOutputHandler {

  private static final String LINK_GENERATED = "link-generated";


  @Override
  public void startElement(StartElement startElement, ExecutionEnvironment env) throws XMLStreamException {
    Attribute href = startElement.getAttributeByName(RTAttributes.HREF.getQName());
    if (href != null) {
      String value = href.getValue();
      if (value != null) {
        if (IdHelper.isContentId(value)) {
          ContentProxy contentProxy = env.getProxyFactory().makeContentProxy(value);
          if (contentProxy != null) {
            String link = env.getLinkBuilder().createLink(contentProxy);
            if (!Strings.isNullOrEmpty(link)) {
              env.getWriter().writeStartElement("a");
              env.getWriter().writeAttribute("href", "#none");
              env.getWriter().writeAttribute("cms-href", link);
              // mark link generated for closing tag
              env.setAttribute(LINK_GENERATED, true);
            }
          }
        }
        else {
          // assume external link
          env.getWriter().writeStartElement("a");
          env.getWriter().writeAttribute("href", value);
          env.setAttribute(LINK_GENERATED, true);
        }
      }
    }
  }

  @Override
  public void endElement(EndElement endElement, ExecutionEnvironment env) throws XMLStreamException {
    Boolean linkGenerated = (Boolean) env.removeAttribute(LINK_GENERATED, Boolean.FALSE);
    if (linkGenerated) {
      env.getWriter().writeEndElement();
    }
  }

  @Override
  public void characters(Characters characters, ExecutionEnvironment env) throws XMLStreamException {
    env.getWriter().writeCharacters(characters.getData());
  }
}
