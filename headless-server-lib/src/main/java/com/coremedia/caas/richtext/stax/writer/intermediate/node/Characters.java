package com.coremedia.caas.richtext.stax.writer.intermediate.node;

import com.coremedia.caas.richtext.stax.writer.intermediate.TreeVisitor;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationContext;

import javax.xml.stream.XMLStreamException;

public class Characters extends AbstractNode {

  private String data;


  public Characters(String data) {
    this.data = data;
  }


  @Override
  public boolean isCharacter() {
    return true;
  }

  @Override
  public void addAttribute(Attribute attribute) throws XMLStreamException {
    throw new XMLStreamException("Character data has no attributes");
  }

  @Override
  public void addChild(Node child) throws XMLStreamException {
    throw new XMLStreamException("Character data has no children");
  }


  @Override
  public <E> E accept(TreeVisitor<E> visitor, EvaluationContext context) {
    return visitor.acceptCharacters(this, context);
  }


  public String getData() {
    return data;
  }
}
