package com.coremedia.caas.richtext.output;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.richtext.stax.writer.intermediate.IntermediateTree;

public interface OutputFactory<E> {

  E transform(IntermediateTree tree, ExecutionContext executionContext);
}
