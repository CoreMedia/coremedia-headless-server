package com.coremedia.caas.richtext.stax.writer.intermediate.node;

import com.coremedia.caas.richtext.stax.writer.intermediate.TreeVisitor;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationContext;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Element extends AbstractNode {

  private String name;
  private Function<EvaluationContext, String> function;
  private List<Attribute> attributes = new LinkedList<>();
  private List<Node> children = new LinkedList<>();


  public Element(String name) {
    this.name = name;
  }

  public Element(Function<EvaluationContext, String> function) {
    this.function = function;
  }


  @Override
  public boolean isElement() {
    return true;
  }

  @Override
  public void addAttribute(Attribute attribute) {
    attributes.add(attribute);
  }

  @Override
  public void addChild(Node child) {
    child.setParent(this);
    children.add(child);
  }


  @Override
  public <E> E accept(TreeVisitor<E> visitor, EvaluationContext context) {
    return visitor.acceptElement(this, context);
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

  public List<Node> getChildren() {
    return children;
  }
}
