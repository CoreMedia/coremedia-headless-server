package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.richtext.RichtextTransformer;
import com.coremedia.caas.richtext.RichtextTransformerRegistry;
import com.coremedia.caas.richtext.output.StringOutputFactory;
import com.coremedia.caas.richtext.output.TreeOutputFactory;
import com.coremedia.caas.schema.type.scalar.RichtextTree;
import com.coremedia.caas.service.expression.FieldExpression;
import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.caas.service.repository.content.MarkupProxy;

import graphql.Scalars;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLOutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.coremedia.caas.service.repository.content.util.ContentUtil.isNullOrEmptyRichtext;

public class RichtextPropertyDataFetcher extends AbstractPropertyDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(RichtextPropertyDataFetcher.class);


  public RichtextPropertyDataFetcher(FieldExpression<?> expression, List<FieldExpression<?>> fallbackExpressions) {
    super(expression, fallbackExpressions);
  }


  @Override
  protected boolean isNullOrEmpty(Object value) {
    return isNullOrEmptyRichtext(value);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, FieldExpression<?> expression, DataFetchingEnvironment environment) {
    ExecutionContext context = getContext(environment);
    MarkupProxy markupProxy = getProperty(contentProxy, expression, MarkupProxy.class);
    if (markupProxy != null && !markupProxy.isEmpty()) {
      String view = getArgumentWithDefault("view", "default", environment);
      // get matching transformer and convert markup
      RichtextTransformerRegistry registry = context.getProcessingDefinition().getRichtextTransformerRegistry();
      RichtextTransformer transformer = registry.getTransformer(view);
      if (transformer != null) {
        try {
          GraphQLOutputType outputType = environment.getFieldType();
          if (RichtextTree.RICHTEXT_TREE.getName().equals(outputType.getName())) {
            return transformer.transform(markupProxy, new TreeOutputFactory(), context);
          }
          else if (Scalars.GraphQLString.getName().equals(outputType.getName())) {
            return transformer.transform(markupProxy, new StringOutputFactory(), context);
          }
          else {
            LOG.error("Unsupported richtext field type: {}", outputType.getName());
          }
        } catch (Exception e) {
          LOG.error("Richtext transformation failed:", e);
        }
      }
    }
    return null;
  }
}
