package com.coremedia.caas.schema.datafetcher.content.model;

import com.coremedia.caas.schema.util.FieldExpressionEvaluator;
import com.coremedia.caas.service.repository.content.ContentProxy;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.expression.Expression;

public class NavigationModelDataFetcher extends AbstractModelDataFetcher {

  private Expression expression;


  public NavigationModelDataFetcher(String sourceName, String modelName) {
    super(sourceName, modelName);
    this.expression = FieldExpressionEvaluator.compile(sourceName);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, String modelName, String sourceName, DataFetchingEnvironment environment) {
    Object model = getContext(environment).getRootContext().getModelFactory().createModel(modelName, sourceName, contentProxy);
    if (model != null) {
      return FieldExpressionEvaluator.fetch(expression, model);
    }
    return null;
  }
}
