package com.coremedia.caas.richtext.common;

import javax.xml.namespace.QName;

public enum RTAttributes {

  CLASS("class"),
  HREF("http://www.w3.org/1999/xlink", "href");


  private QName qname;


  RTAttributes(String localPart) {
    this.qname = new QName(localPart);
  }

  RTAttributes(String namespaceURI, String localPart) {
    this.qname = new QName(namespaceURI, localPart);
  }


  public QName getQName() {
    return qname;
  }
}
