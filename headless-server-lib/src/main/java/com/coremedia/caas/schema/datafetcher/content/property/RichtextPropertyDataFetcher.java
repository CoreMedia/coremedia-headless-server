package com.coremedia.caas.schema.datafetcher.content.property;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.richtext.RichtextTransformer;
import com.coremedia.caas.richtext.RichtextTransformerRegistry;
import com.coremedia.caas.richtext.output.StringOutputFactory;
import com.coremedia.caas.richtext.output.TreeOutputFactory;
import com.coremedia.caas.schema.type.scalar.RichtextTree;
import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.caas.service.repository.content.MarkupProxy;

import graphql.Scalars;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLOutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class RichtextPropertyDataFetcher extends AbstractPropertyDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(RichtextPropertyDataFetcher.class);


  public RichtextPropertyDataFetcher(String sourceName, List<String> fallbackSourceNames) {
    super(sourceName, fallbackSourceNames);
  }


  @Override
  protected Object getData(ContentProxy contentProxy, String sourceName, DataFetchingEnvironment environment) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    ExecutionContext context = getContext(environment);
    MarkupProxy markup = getProperty(contentProxy, sourceName);
    if (markup != null) {
      String view = getArgumentWithDefault("view", "default", environment);
      // get matching transformer and convert markup
      RichtextTransformerRegistry registry = context.getProcessingDefinition().getRichtextTransformerRegistry();
      RichtextTransformer transformer = registry.getTransformer(view);
      if (transformer != null) {
        try {
          GraphQLOutputType outputType = environment.getFieldType();
          if (RichtextTree.RICHTEXT_TREE.getName().equals(outputType.getName())) {
            return transformer.transform(markup, new TreeOutputFactory(), context);
          }
          else if (Scalars.GraphQLString.getName().equals(outputType.getName())) {
            return transformer.transform(markup, new StringOutputFactory(), context);
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
