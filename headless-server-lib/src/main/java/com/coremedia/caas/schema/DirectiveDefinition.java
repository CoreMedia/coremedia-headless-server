package com.coremedia.caas.schema;

import java.util.Map;

public interface DirectiveDefinition {

  String getName();

  Map<String, Object> getArguments();
}
