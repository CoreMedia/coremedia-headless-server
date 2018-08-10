package com.coremedia.caas.richtext.stax.writer.intermediate;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationContext;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.Evaluator;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Attribute;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Block;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Characters;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Element;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.EmptyElement;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Node;
import com.coremedia.caas.service.cache.Weighted;

import java.util.function.Function;
import javax.xml.stream.XMLStreamException;

public class IntermediateTree implements Weighted {

  /*
   * Poor approximations for calculating the required space
   */
  private static final int WEIGHT_BLOCK = 32;
  private static final int WEIGHT_ELEMENT = 128;
  private static final int WEIGHT_EMPTY_ELEMENT = 80;
  private static final int WEIGHT_CHARACTERS = 88;
  private static final int WEIGHT_ATTRIBUTE = 48;
  private static final int WEIGHT_FUNCTION = 40;


  private int weight = WEIGHT_ELEMENT;

  private Node root = new Element("div");
  private Node currentNode = root;


  @Override
  public int getWeight() {
    return weight;
  }


  public <E> E accept(TreeVisitor<E> visitor, ExecutionContext executionContext) {
    return root.accept(visitor, new EvaluationContext(executionContext));
  }


  public void startElement(String name) throws XMLStreamException {
    addChild(new Element(name));
    // update weight
    weight += WEIGHT_ELEMENT;
  }

  public void startElement(Function<EvaluationContext, String> function) throws XMLStreamException {
    addChild(new Element(function));
    // update weight
    weight += WEIGHT_ELEMENT + WEIGHT_FUNCTION;
  }

  public void endElement() {
    closeEmpty();
    currentNode = currentNode.getParent();
  }

  public void emptyElement(String name) throws XMLStreamException {
    addChild(new EmptyElement(name));
    // update weight
    weight += WEIGHT_EMPTY_ELEMENT;
  }

  public void emptyElement(Function<EvaluationContext, String> function) throws XMLStreamException {
    addChild(new EmptyElement(function));
    // update weight
    weight += WEIGHT_EMPTY_ELEMENT + WEIGHT_FUNCTION;
  }


  public void characters(String data) throws XMLStreamException {
    closeEmpty();
    currentNode.addChild(new Characters(data));
    // update weight
    weight += WEIGHT_CHARACTERS + data.length() * 2;

  }

  public void attribute(String name, String value) throws XMLStreamException {
    currentNode.addAttribute(new Attribute(name, value));
    // update weight
    weight += WEIGHT_ATTRIBUTE;
  }

  public void attribute(String name, Function<EvaluationContext, String> function) throws XMLStreamException {
    currentNode.addAttribute(new Attribute(name, function));
    // update weight
    weight += WEIGHT_ATTRIBUTE + WEIGHT_FUNCTION;
  }

  public void startBlock(Evaluator evaluator) throws XMLStreamException {
    addChild(new Block(evaluator));
    // update weight
    weight += WEIGHT_BLOCK + WEIGHT_FUNCTION;
  }

  public void endBlock() {
    closeEmpty();
    currentNode = currentNode.getParent();
  }


  private void closeEmpty() {
    if (currentNode.isEmptyElement()) {
      currentNode = currentNode.getParent();
    }
  }

  private void addChild(Node node) throws XMLStreamException {
    closeEmpty();
    currentNode.addChild(node);
    currentNode = node;
  }
}
