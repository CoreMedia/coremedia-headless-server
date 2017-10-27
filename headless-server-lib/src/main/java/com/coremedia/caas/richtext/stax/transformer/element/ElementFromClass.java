package com.coremedia.caas.richtext.stax.transformer.element;

import com.coremedia.caas.richtext.common.RTAttributes;
import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.google.common.base.Splitter;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.util.Map;

public class ElementFromClass implements ElementTransformer {

  private static final Splitter CLASS_SPLITTER = Splitter.on(' ').omitEmptyStrings();


  private Map<String, String> mapping;


  public Map<String, String> getMapping() {
    return mapping;
  }

  public void setMapping(Map<String, String> mapping) {
    this.mapping = mapping;
  }


  @Override
  public QName getName(StartElement startElement, ExecutionEnvironment env) {
    Attribute attribute = startElement.getAttributeByName(RTAttributes.CLASS.getQName());
    if (attribute != null) {
      for (String className : CLASS_SPLITTER.splitToList(attribute.getValue())) {
        String elementName = mapping.get(className);
        if (elementName != null) {
          return new QName(elementName);
        }
      }
    }
    return startElement.getName();
  }
}
