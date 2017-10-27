package com.coremedia.caas.richtext.stax.handler.matcher;

import javax.xml.stream.events.StartElement;

public interface Matcher {

  boolean matches(StartElement startElement);
}
