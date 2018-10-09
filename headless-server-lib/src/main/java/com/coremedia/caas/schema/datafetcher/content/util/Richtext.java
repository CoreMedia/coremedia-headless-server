package com.coremedia.caas.schema.datafetcher.content.util;

import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.richtext.RichtextTransformer;
import com.coremedia.caas.richtext.RichtextTransformerRegistry;
import com.coremedia.caas.richtext.output.StringOutputFactory;
import com.coremedia.caas.richtext.output.TreeOutputFactory;
import com.coremedia.caas.schema.type.scalar.RichtextTree;
import com.coremedia.caas.service.repository.content.MarkupProxy;

import graphql.Scalars;
import graphql.schema.GraphQLOutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Richtext {

  private static final Logger LOG = LoggerFactory.getLogger(Richtext.class);


  public static final String NAME = "Richtext";


  private MarkupProxy markupProxy;
  private String view;


  public Richtext(MarkupProxy markupProxy, String view) {
    this.markupProxy = markupProxy;
    this.view = view;
  }


  public MarkupProxy getMarkupProxy() {
    return markupProxy;
  }


  public String getView() {
    return view;
  }

  public void setView(String view) {
    this.view = view;
  }


  public Object transform(GraphQLOutputType outputType, ExecutionContext context) {
    RichtextTransformerRegistry registry = context.getProcessingDefinition().getRichtextTransformerRegistry();
    RichtextTransformer transformer = registry.getTransformer(view);
    if (transformer != null) {
      try {
        if (RichtextTree.CmsRichtextTree.getName().equals(outputType.getName())) {
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
    return null;
  }
}
