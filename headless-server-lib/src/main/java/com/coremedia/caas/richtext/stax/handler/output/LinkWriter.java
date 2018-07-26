package com.coremedia.caas.richtext.stax.handler.output;

import com.coremedia.caas.richtext.common.RTAttributes;
import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationAction;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationContext;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.Evaluator;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.FunctionEvaluator;
import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.cap.common.IdHelper;

import com.google.common.base.Strings;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

public class LinkWriter extends AbstractOutputHandler {

  private static final String LINK_GENERATED_KEY = "link-generated";

  private static final String LINK_GENERATED_INTERNAL = "internal";
  private static final String LINK_GENERATED_EXTERNAL = "external";


  @Override
  public void startElement(StartElement startElement, ExecutionEnvironment env) throws XMLStreamException {
    Attribute href = startElement.getAttributeByName(RTAttributes.HREF.getQName());
    if (href != null) {
      String value = href.getValue();
      if (value != null) {
        if (IdHelper.isContentId(value)) {
          Evaluator evaluator = new FunctionEvaluator((context) -> {
            ContentProxy contentProxy = context.getProxyFactory().makeContentProxyFromId(value);
            if (contentProxy == null) {
              // don't output link tag but all nested nodes
              return EvaluationAction.INCLUDE_INNER;
            }
            else {
              String link = context.getLinkBuilder().createLink(contentProxy);
              if (Strings.isNullOrEmpty(link)) {
                // don't output link tag but all nested nodes
                return EvaluationAction.INCLUDE_INNER;
              }
              // add properties to context
              context.add("link", link);
              return EvaluationAction.INCLUDE;
            }
          });
          env.getWriter().writeStartBlock(evaluator);
          env.getWriter().writeStartElement("a");
          env.getWriter().writeAttribute("data-href", EvaluationContext.source("link"));
          // mark link generated for closing tag
          env.setAttribute(LINK_GENERATED_KEY, LINK_GENERATED_INTERNAL);
        }
        else {
          // assume external link
          env.getWriter().writeStartElement("a");
          env.getWriter().writeAttribute("href", value);
          // mark link generated for closing tag
          env.setAttribute(LINK_GENERATED_KEY, LINK_GENERATED_EXTERNAL);
        }
      }
    }
  }

  @Override
  public void endElement(EndElement endElement, ExecutionEnvironment env) throws XMLStreamException {
    String linkType = (String) env.removeAttribute(LINK_GENERATED_KEY, "");
    if (LINK_GENERATED_INTERNAL.equals(linkType)) {
      env.getWriter().writeEndElement();
      env.getWriter().writeEndBlock();
    }
    else if (LINK_GENERATED_EXTERNAL.equals(linkType)) {
      env.getWriter().writeEndElement();
    }
  }

  @Override
  public void characters(Characters characters, ExecutionEnvironment env) throws XMLStreamException {
    env.getWriter().writeCharacters(characters.getData());
  }
}
