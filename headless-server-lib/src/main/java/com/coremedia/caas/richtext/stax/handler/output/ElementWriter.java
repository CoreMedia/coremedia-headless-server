package com.coremedia.caas.richtext.stax.handler.output;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.transformer.attribute.AttributeTransformer;
import com.coremedia.caas.richtext.stax.transformer.element.ElementTransformer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import java.util.List;

public class ElementWriter extends AbstractOutputHandler {

  private boolean writeElement = false;
  private boolean writeCharacters = false;

  private ElementTransformer elementTransformer;

  private List<AttributeTransformer> attributeTransformers;


  public boolean isWriteElement() {
    return writeElement;
  }

  public void setWriteElement(boolean writeElement) {
    this.writeElement = writeElement;
  }

  public boolean isWriteCharacters() {
    return writeCharacters;
  }

  public void setWriteCharacters(boolean writeCharacters) {
    this.writeCharacters = writeCharacters;
  }


  public ElementTransformer getElementTransformer() {
    return elementTransformer;
  }

  public void setElementTransformer(ElementTransformer elementTransformer) {
    this.elementTransformer = elementTransformer;
  }

  public List<AttributeTransformer> getAttributeTransformers() {
    return attributeTransformers;
  }

  public void setAttributeTransformers(List<AttributeTransformer> attributeTransformers) {
    this.attributeTransformers = attributeTransformers;
  }


  @Override
  public void startElement(StartElement startElement, ExecutionEnvironment env) throws XMLStreamException {
    if (writeElement) {
      if (elementTransformer != null) {
        env.getWriter().writeStartElement(elementTransformer.getName(startElement, env).getLocalPart());
      } else {
        env.getWriter().writeStartElement(startElement.getName().getLocalPart());
      }
    }
    if (attributeTransformers != null) {
      for (AttributeTransformer transformer : attributeTransformers) {
        Attribute attribute = transformer.getAttribute(startElement, env);
        if (attribute != null) {
          env.getWriter().writeAttribute(attribute.getName().getLocalPart(), attribute.getValue());
        }
      }
    }
  }

  @Override
  public void endElement(EndElement endElement, ExecutionEnvironment env) throws XMLStreamException {
    if (writeElement) {
      env.getWriter().writeEndElement();
    }
  }

  @Override
  public void characters(Characters characters, ExecutionEnvironment env) throws XMLStreamException {
    if (isWriteCharacters()) {
      env.getWriter().writeCharacters(characters.getData());
    }
  }
}
