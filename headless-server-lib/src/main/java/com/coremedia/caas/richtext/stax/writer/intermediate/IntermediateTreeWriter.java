package com.coremedia.caas.richtext.stax.writer.intermediate;

import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationContext;
import com.coremedia.caas.richtext.stax.writer.intermediate.eval.Evaluator;

import java.util.function.Function;
import javax.xml.stream.XMLStreamException;

public class IntermediateTreeWriter {

  private IntermediateTree intermediateTree = new IntermediateTree();


  public IntermediateTree getOutput() {
    return intermediateTree;
  }


  public void writeStartElement(String name) throws XMLStreamException {
    intermediateTree.startElement(name);
  }

  public void writeStartElement(Function<EvaluationContext, String> function) throws XMLStreamException {
    intermediateTree.startElement(function);
  }

  public void writeAttribute(String name, String value) throws XMLStreamException {
    intermediateTree.attribute(name, value);
  }

  public void writeAttribute(String name, Function<EvaluationContext, String> function) throws XMLStreamException {
    intermediateTree.attribute(name, function);
  }

  public void writeEndElement() {
    intermediateTree.endElement();
  }

  public void writeCharacters(String data) throws XMLStreamException {
    intermediateTree.characters(data);
  }

  public void writeEmptyElement(String name) throws XMLStreamException {
    intermediateTree.emptyElement(name);
  }

  public void writeEmptyElement(Function<EvaluationContext, String> function) throws XMLStreamException {
    intermediateTree.emptyElement(function);
  }

  public void writeStartBlock(Evaluator evaluator) throws XMLStreamException {
    intermediateTree.startBlock(evaluator);
  }

  public void writeEndBlock() {
    intermediateTree.endBlock();
  }
}
