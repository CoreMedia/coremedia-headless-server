package com.coremedia.caas.schema.directive;

public interface EvaluationResult {

  EvaluationResult NOOP = new EvaluationResult() {
    @Override
    public Action getAction() {
      return Action.NOOP;
    }

    @Override
    public Object getValue() {
      return null;
    }
  };


  Action getAction();

  Object getValue();
}
