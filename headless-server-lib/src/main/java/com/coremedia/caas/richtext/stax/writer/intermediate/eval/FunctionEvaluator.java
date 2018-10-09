package com.coremedia.caas.richtext.stax.writer.intermediate.eval;

import java.util.function.Function;

public class FunctionEvaluator implements Evaluator {

  private Function<EvaluationContext, EvaluationAction> function;


  public FunctionEvaluator(Function<EvaluationContext, EvaluationAction> function) {
    this.function = function;
  }


  @Override
  public EvaluationAction evaluate(EvaluationContext context) {
    return function.apply(context);
  }
}
