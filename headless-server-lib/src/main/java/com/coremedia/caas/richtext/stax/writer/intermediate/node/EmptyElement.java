package com.coremedia.caas.richtext.stax.writer.intermediate.node;

import com.coremedia.caas.richtext.stax.writer.intermediate.TreeVisitor;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationContext;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import javax.xml.stream.XMLStreamException;

public class EmptyElement extends AbstractNode {

  private String name;
  private Function<EvaluationContext, String> function;
  private List<Attribute> attributes = new LinkedList<>();


  public EmptyElement(String name) {
    this.name = name;
  }

  public EmptyElement(Function<EvaluationContext, String> function) {
    this.function = function;
  }


  @Override
  public boolean isEmptyElement() {
    return true;
  }

  @Override
  public void addAttribute(Attribute attribute) {
    attributes.add(attribute);
  }

  @Override
  public void addChild(Node child) throws XMLStreamException {
    throw new XMLStreamException("Empty element has no children");
  }


  @Override
  public <E> E accept(TreeVisitor<E> visitor, EvaluationContext context) {
    return visitor.acceptEmptyElement(this, context);
  }


  public String getName(EvaluationContext context) {
    if (function != null) {
      return function.apply(context);
    }
    return name;
  }

  public List<Attribute> getAttributes() {
    return attributes;
  }
}
