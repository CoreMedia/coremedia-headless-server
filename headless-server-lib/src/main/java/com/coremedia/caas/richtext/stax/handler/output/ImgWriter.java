package com.coremedia.caas.richtext.stax.handler.output;

import com.coremedia.caas.richtext.common.RTAttributes;
import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.transformer.attribute.AttributeTransformer;
import com.coremedia.cap.common.CapBlobRef;
import com.coremedia.cap.common.IdHelper;
import com.google.common.base.Strings;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.util.List;

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
        CapBlobRef blobRef = env.getContentRepository().getBlobRef(value);
        if (blobRef != null) {
          String link = env.getLinkBuilder().createLink(blobRef);
          String alt = null;
          if (blobRef.getCapObject().getType().isSubtypeOf("CMMedia")) {
            alt = blobRef.getCapObject().getString("alt");
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
