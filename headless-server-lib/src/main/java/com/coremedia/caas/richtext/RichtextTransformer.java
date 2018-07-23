package com.coremedia.caas.richtext;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.richtext.output.OutputFactory;
import com.coremedia.xml.Markup;

public interface RichtextTransformer {

  String getView();

  <E> E transform(Markup markup, OutputFactory<E> outputFactory, ExecutionContext executionContext) throws Exception;
}
