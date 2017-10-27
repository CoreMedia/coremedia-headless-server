package com.coremedia.caas.richtext.stax.handler.matcher;

import com.coremedia.caas.richtext.common.RTAttributes;
import com.google.common.base.Splitter;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.util.List;

public class SimpleTagMatcher implements Matcher {

  private static final Splitter CLASS_SPLITTER = Splitter.on(' ').omitEmptyStrings();


  private QName qName;
  private List<String> classes;


  public QName getQname() {
    return qName;
  }

  public void setQname(QName qName) {
    this.qName = qName;
  }

  public List<String> getClasses() {
    return classes;
  }

  public void setClasses(List<String> classes) {
    this.classes = classes;
  }


  @Override
  public boolean matches(StartElement startElement) {
    if (qName != null && !qName.equals(startElement.getName())) {
      return false;
    }
    if (classes != null) {
      Attribute attribute = startElement.getAttributeByName(RTAttributes.CLASS.getQName());
      if (attribute != null) {
        List<String> classNames = CLASS_SPLITTER.splitToList(attribute.getValue());
        return classes.stream().anyMatch(classNames::contains);
      }
      return false;
    }
    return true;
  }
}
