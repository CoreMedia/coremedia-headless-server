package com.coremedia.caas.richtext.stax.writer.intermediate;

import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationContext;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Block;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Characters;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Element;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.EmptyElement;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Node;
import com.coremedia.caas.richtext.stax.writer.transfer.AbstractRepresentation;
import com.coremedia.caas.richtext.stax.writer.transfer.AttributeRepresentation;
import com.coremedia.caas.richtext.stax.writer.transfer.CharactersRepresentation;
import com.coremedia.caas.richtext.stax.writer.transfer.ElementRepresentation;
import com.coremedia.caas.richtext.stax.writer.transfer.EmptyElementRepresentation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TreeToTransferTreeVisitor implements TreeVisitor<List<AbstractRepresentation>> {

  @Override
  public List<AbstractRepresentation> acceptCharacters(Characters characters, EvaluationContext context) {
    return Collections.singletonList(new CharactersRepresentation(characters.getData()));
  }

  @Override
  public List<AbstractRepresentation> acceptElement(Element element, EvaluationContext context) {
    List<AttributeRepresentation> attributes = element.getAttributes().stream().map(e -> new AttributeRepresentation(e.getName(context), e.getValue(context))).collect(Collectors.toList());
    List<AbstractRepresentation> children = element.getChildren().stream().map(e -> e.accept(this, context)).flatMap(Collection::stream).collect(Collectors.toList());
    return Collections.singletonList(new ElementRepresentation(element.getName(context), attributes, children));
  }

  @Override
  public List<AbstractRepresentation> acceptEmptyElement(EmptyElement emptyElement, EvaluationContext context) {
    List<AttributeRepresentation> attributes = emptyElement.getAttributes().stream().map(e -> new AttributeRepresentation(e.getName(context), e.getValue(context))).collect(Collectors.toList());
    return Collections.singletonList(new EmptyElementRepresentation(emptyElement.getName(context), attributes));
  }

  @Override
  public List<AbstractRepresentation> acceptBlock(Block block, EvaluationContext context) {
    EvaluationContext subcontext = context.createSubcontext();
    // handle children according to result
    switch (block.evaluate(subcontext)) {
      case INCLUDE: {
        return block.getChild().accept(this, subcontext);
      }
      case INCLUDE_INNER: {
        Node child = block.getChild();
        if (child.isElement() && !child.isEmptyElement()) {
          return ((Element) child).getChildren().stream().map(e -> e.accept(this, subcontext)).flatMap(Collection::stream).collect(Collectors.toList());
        }
      }
    }
    // drop case: generate no output at all
    return Collections.emptyList();
  }
}
