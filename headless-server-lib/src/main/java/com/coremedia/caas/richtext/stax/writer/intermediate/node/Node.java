package com.coremedia.caas.richtext.stax.writer.intermediate.node;

import com.coremedia.caas.richtext.stax.writer.intermediate.TreeVisitor;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationContext;

import javax.xml.stream.XMLStreamException;

public interface Node {

  boolean isBlock();

  boolean isCharacter();

  boolean isElement();

  boolean isEmptyElement();

  Node getParent();

  void setParent(Node parent);

  void addAttribute(Attribute attribute) throws XMLStreamException;

  void addChild(Node child) throws XMLStreamException;


  <E> E accept(TreeVisitor<E> visitor, EvaluationContext context);
}
