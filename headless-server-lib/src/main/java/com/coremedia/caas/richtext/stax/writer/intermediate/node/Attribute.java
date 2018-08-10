package com.coremedia.caas.richtext.stax.writer.intermediate.node;

import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationContext;

import java.util.function.Function;

public class Attribute {

  private String name;
  private String value;
  private Function<EvaluationContext, String> nameFunction;
  private Function<EvaluationContext, String> valueFunction;


  public Attribute(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public Attribute(String name, Function<EvaluationContext, String> valueFunction) {
    this.name = name;
    this.valueFunction = valueFunction;
  }

  public Attribute(Function<EvaluationContext, String> nameFunction, Function<EvaluationContext, String> valueFunction) {
    this.nameFunction = nameFunction;
    this.valueFunction = valueFunction;
  }


  public String getName(EvaluationContext context) {
    if (nameFunction != null) {
      return nameFunction.apply(context);
    }
    return name;
  }

  public String getValue(EvaluationContext context) {
    if (valueFunction != null) {
      return valueFunction.apply(context);
    }
    return value;
  }
}
