package com.coremedia.caas.richtext.common;

import javax.xml.namespace.QName;

public enum RTElements {

  BR("http://www.coremedia.com/2003/richtext-1.0", "br"),
  P("http://www.coremedia.com/2003/richtext-1.0", "p");


  private QName qname;


  RTElements(String namespaceURI, String localPart) {
    this.qname = new QName(namespaceURI, localPart);
  }


  public QName getQName() {
    return qname;
  }
}
