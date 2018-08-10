package com.coremedia.caas.richtext.stax.writer.intermediate.node;

public abstract class AbstractNode implements Node {

  private Node parent;


  @Override
  public boolean isEmptyElement() {
    return false;
  }

  @Override
  public boolean isBlock() {
    return false;
  }

  @Override
  public boolean isCharacter() {
    return false;
  }

  @Override
  public boolean isElement() {
    return false;
  }


  public Node getParent() {
    return parent;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }
}
