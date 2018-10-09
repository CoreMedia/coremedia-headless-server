package com.coremedia.caas.richtext.output;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.richtext.stax.writer.intermediate.IntermediateTree;
import com.coremedia.caas.richtext.stax.writer.intermediate.TreeToStringVisitor;

public class StringOutputFactory implements OutputFactory<String> {

  @Override
  public String transform(IntermediateTree tree, ExecutionContext executionContext) {
    return tree.accept(new TreeToStringVisitor(), executionContext);
  }
}
