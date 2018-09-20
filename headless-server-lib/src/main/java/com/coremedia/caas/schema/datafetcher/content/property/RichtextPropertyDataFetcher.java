package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.schema.datafetcher.content.util.Richtext;
import com.coremedia.caas.service.expression.FieldExpression;
import com.coremedia.caas.service.repository.content.MarkupProxy;

import graphql.schema.DataFetchingEnvironment;

import java.util.List;

import static com.coremedia.caas.service.repository.content.util.ContentUtil.isNullOrEmptyRichtext;

public class RichtextPropertyDataFetcher extends AbstractPropertyDataFetcher<Object> {

  public RichtextPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions) {
    super(expression, fallbackExpressions, Object.class);
  }


  @Override
  protected boolean isNullOrEmpty(Object value) {
    if (value instanceof Richtext) {
      value = ((Richtext) value).getMarkupProxy();
    }
    return isNullOrEmptyRichtext(value);
  }

  @Override
  protected Object processResult(Object result, DataFetchingEnvironment environment) {
    if (result instanceof MarkupProxy) {
      // wrap proxy with default view
      String defaultView = getContext(environment).getProcessingDefinition().getDefaultRichtextFormat();
      result = new Richtext((MarkupProxy) result, defaultView != null ? defaultView : "default");
    }
    if (result instanceof Richtext) {
      Richtext richtext = (Richtext) result;
      if (!isNullOrEmptyRichtext(richtext.getMarkupProxy())) {
        // allow optional view override in queries
        String requestedView = getArgument("view", environment);
        if (requestedView != null) {
          richtext.setView(requestedView);
        }
        return richtext.transform(environment.getFieldType(), getContext(environment));
      }
    }
    return null;
  }
}
