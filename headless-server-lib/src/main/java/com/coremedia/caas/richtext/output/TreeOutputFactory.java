package com.coremedia.caas.richtext.output;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.richtext.stax.writer.intermediate.IntermediateTree;
import com.coremedia.caas.richtext.stax.writer.intermediate.TreeToTransferTreeVisitor;

public class TreeOutputFactory implements OutputFactory<Object> {

  @Override
  public Object transform(IntermediateTree tree, ExecutionContext executionContext) {
    return tree.accept(new TreeToTransferTreeVisitor(), executionContext);
  }
}
