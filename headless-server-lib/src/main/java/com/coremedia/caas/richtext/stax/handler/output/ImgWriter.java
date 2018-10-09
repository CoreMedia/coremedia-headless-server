package com.coremedia.caas.richtext.stax.handler.output;

import com.coremedia.caas.richtext.common.RTAttributes;
import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.transformer.attribute.AttributeTransformer;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationAction;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationContext;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.Evaluator;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.FunctionEvaluator;
import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.cap.common.IdHelper;

import com.google.common.base.Strings;

import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

public class ImgWriter extends AbstractOutputHandler {

  private List<AttributeTransformer> attributeTransformers;


  public List<AttributeTransformer> getAttributeTransformers() {
    return attributeTransformers;
  }

  public void setAttributeTransformers(List<AttributeTransformer> attributeTransformers) {
    this.attributeTransformers = attributeTransformers;
  }


  @Override
  public void startElement(StartElement startElement, ExecutionEnvironment env) throws XMLStreamException {
    Attribute href = startElement.getAttributeByName(RTAttributes.HREF.getQName());
    if (href != null) {
      String value = href.getValue();
      if (value != null && IdHelper.isBlobId(value)) {
        Evaluator evaluator = new FunctionEvaluator((context) -> {
          ContentProxy contentProxy = context.getRootContext().getProxyFactory().makeContentProxyFromId(IdHelper.parseContentIdFromBlobId(value));
          if (contentProxy == null) {
            return EvaluationAction.DROP;
          }
          else {
            String link = context.getLinkBuilder().createLink(contentProxy, context.getRootContext()).toString();
            if (Strings.isNullOrEmpty(link)) {
              return EvaluationAction.DROP;
            }
            String alt = contentProxy.isSubtypeOf("CMMedia") ? contentProxy.getString("alt") : "";
            // add properties to context
            context.add("link", link);
            context.add("alt", alt);
            return EvaluationAction.INCLUDE;
          }
        });
        env.getWriter().writeStartBlock(evaluator);
        env.getWriter().writeEmptyElement("img");
        env.getWriter().writeAttribute("data-src", EvaluationContext.source("link"));
        env.getWriter().writeAttribute("alt", EvaluationContext.source("alt"));
        if (attributeTransformers != null) {
          for (AttributeTransformer transformer : attributeTransformers) {
            Attribute attribute = transformer.getAttribute(startElement, env);
            if (attribute != null) {
              env.getWriter().writeAttribute(attribute.getName().getLocalPart(), attribute.getValue());
            }
          }
        }
        env.getWriter().writeEndBlock();
      }
    }
  }
}
