package com.coremedia.caas.richtext.stax.writer.intermediate;

import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationContext;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Block;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Characters;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Element;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.EmptyElement;

public interface TreeVisitor<E> {

  E acceptBlock(Block block, EvaluationContext context);

  E acceptCharacters(Characters characters, EvaluationContext context);

  E acceptElement(Element element, EvaluationContext context);

  E acceptEmptyElement(EmptyElement emptyElement, EvaluationContext context);
}
