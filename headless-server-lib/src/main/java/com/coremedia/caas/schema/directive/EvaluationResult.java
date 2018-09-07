package com.coremedia.caas.schema.directive;

public class EvaluationResult {

  public static EvaluationResult NOOP = new EvaluationResult(Action.NOOP, null);
  public static EvaluationResult RETNULL = new EvaluationResult(Action.RETURN, null);


  private Action action;
  private Object value;


  public EvaluationResult(Action action, Object value) {
    this.action = action;
    this.value = value;
  }


  public Action getAction() {
    return action;
  }

  public Object getValue() {
    return value;
  }
}
