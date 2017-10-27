package com.coremedia.caas.richtext.stax.transformer.attribute;

import com.coremedia.caas.richtext.common.RTAttributes;
import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.util.List;
import java.util.stream.Collectors;

public class PassStyles implements AttributeTransformer {

  private static final Joiner CLASS_JOINER = Joiner.on(' ');

  private static final Splitter CLASS_SPLITTER = Splitter.on(' ').omitEmptyStrings();


  private List<String> styles;


  public List<String> getStyles() {
    return styles;
  }

  public void setStyles(List<String> styles) {
    this.styles = styles;
  }


  @Override
  public Attribute getAttribute(StartElement startElement, ExecutionEnvironment env) {
    Attribute attribute = startElement.getAttributeByName(RTAttributes.CLASS.getQName());
    if (attribute != null) {
      List<String> validNames = CLASS_SPLITTER.splitToList(attribute.getValue()).stream().filter(styles::contains).collect(Collectors.toList());
      if (!validNames.isEmpty()) {
        return env.getEventFactory().createAttribute(RTAttributes.CLASS.getQName(), CLASS_JOINER.join(validNames));
      }
    }
    return null;
  }
}
