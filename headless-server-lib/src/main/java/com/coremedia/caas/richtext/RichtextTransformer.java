package com.coremedia.caas.richtext;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.xml.Markup;

public interface RichtextTransformer<E> {

  String getView();

  E transform(Markup markup, ExecutionContext executionContext) throws Exception;
}
