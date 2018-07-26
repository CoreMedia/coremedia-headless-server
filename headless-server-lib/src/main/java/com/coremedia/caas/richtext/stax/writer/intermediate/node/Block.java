package com.coremedia.caas.richtext.stax.writer.intermediate.node;

import com.coremedia.caas.richtext.stax.writer.intermediate.TreeVisitor;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationAction;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationContext;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.Evaluator;

import javax.xml.stream.XMLStreamException;

public class Block extends AbstractNode {

  private Evaluator evaluator;
  private Node child;


  public Block(Evaluator evaluator) {
    this.evaluator = evaluator;
  }


  @Override
  public boolean isBlock() {
    return true;
  }

  @Override
  public void addAttribute(Attribute attribute) throws XMLStreamException {
    throw new XMLStreamException("Block has no attributes");
  }

  @Override
  public void addChild(Node child) throws XMLStreamException {
    if (this.child != null) {
      throw new XMLStreamException("Block can only have one child");
    }
    child.setParent(this);
    this.child = child;
  }


  @Override
  public <E> E accept(TreeVisitor<E> visitor, EvaluationContext context) {
    return visitor.acceptBlock(this, context);
  }


  public Node getChild() {
    return child;
  }


  public EvaluationAction evaluate(EvaluationContext context) {
    return evaluator.evaluate(context);
  }
}
