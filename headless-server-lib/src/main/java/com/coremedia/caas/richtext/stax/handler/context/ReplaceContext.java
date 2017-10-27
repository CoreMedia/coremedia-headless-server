package com.coremedia.caas.richtext.stax.handler.context;

import com.coremedia.caas.richtext.stax.ExecutionEnvironment;
import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;
import com.coremedia.caas.richtext.stax.context.ParseContext;

import javax.xml.stream.events.StartElement;

public class ReplaceContext extends AbstractContextHandler {

  private String replacementName;
  private ParseContext replacement;


  public String getReplacementName() {
    return replacementName;
  }

  public void setReplacementName(String replacementName) {
    this.replacementName = replacementName;
  }


  public ParseContext getReplacement() {
    return replacement;
  }


  @Override
  public void startElement(StartElement startElement, ExecutionEnvironment env) {
    env.replaceContext(getReplacement());
  }


  @Override
  public void resolveReferences(StaxTransformationConfig config) {
    super.resolveReferences(config);
    replacement = resolveContextReference(config, replacementName);
  }
}
