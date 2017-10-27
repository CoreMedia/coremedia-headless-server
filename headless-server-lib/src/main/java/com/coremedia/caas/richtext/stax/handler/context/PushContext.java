package com.coremedia.caas.richtext.stax.handler.context;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;
import com.coremedia.caas.richtext.stax.context.ParseContext;

import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

public class PushContext extends AbstractContextHandler {

  private String contextName;
  private ParseContext context;


  public String getContextName() {
    return contextName;
  }

  public void setContextName(String contextName) {
    this.contextName = contextName;
  }


  public ParseContext getContext() {
    return context;
  }


  @Override
  public void startElement(StartElement startElement, ExecutionEnvironment env) {
    env.pushContext(getContext());
  }

  @Override
  public void endElement(EndElement endElement, ExecutionEnvironment env) {
    env.popContext(getContext());
  }


  @Override
  public void resolveReferences(StaxTransformationConfig config) {
    super.resolveReferences(config);
    context = resolveContextReference(config, contextName);
  }
}
