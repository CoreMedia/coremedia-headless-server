package com.coremedia.caas.richtext.stax.handler.output;

import com.coremedia.caas.richtext.common.RTAttributes;
import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.transformer.attribute.AttributeTransformer;
import com.coremedia.caas.services.repository.content.ContentProxy;
import com.coremedia.cap.common.Blob;
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
        ContentProxy contentProxy = env.getProxyFactory().makeContentProxy(IdHelper.parseContentIdFromBlobId(value));
        if (contentProxy != null) {
          Blob blob = contentProxy.getBlob(IdHelper.parsePropertyFromBlobId(value));
          if (blob != null) {
            String link = env.getLinkBuilder().createLink(contentProxy);
            String alt = null;
            if (contentProxy.isSubtypeOf("CMMedia")) {
              alt = contentProxy.getString("alt");
            }
            if (!Strings.isNullOrEmpty(link)) {
              env.getWriter().writeEmptyElement("img");
              env.getWriter().writeAttribute("cms-src", link);
              if (!Strings.isNullOrEmpty(alt)) {
                env.getWriter().writeAttribute("alt", alt);
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
          }
        }
      }
    }
  }
}
