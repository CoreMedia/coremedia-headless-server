package com.coremedia.caas.richtext;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.richtext.output.OutputFactory;
import com.coremedia.caas.service.repository.content.MarkupProxy;

public interface RichtextTransformer {

  String getView();

  <E> E transform(MarkupProxy markupProxy, OutputFactory<E> outputFactory, ExecutionContext executionContext) throws Exception;
}
