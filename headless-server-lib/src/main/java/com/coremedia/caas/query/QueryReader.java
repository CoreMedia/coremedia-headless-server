package com.coremedia.caas.query;

import com.coremedia.caas.config.loader.ConfigResourceLoader;
import com.coremedia.caas.config.loader.LoaderError;
import com.coremedia.caas.config.reader.AbstractConfigReader;
import com.coremedia.caas.config.reader.CommandCallbackHandler;
import com.coremedia.caas.config.reader.ConfigResource;
import com.coremedia.caas.schema.InvalidQueryDefinition;
import com.coremedia.caas.schema.SchemaService;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryReader extends AbstractConfigReader {

  private static final Logger LOG = LoggerFactory.getLogger(QueryReader.class);


  public QueryReader(ConfigResourceLoader resourceLoader) {
    super(resourceLoader);
  }


  public QueryRegistry read(SchemaService schema) throws InvalidQueryDefinition, IOException {
    ImmutableList.Builder<QueryDefinition> builder = ImmutableList.builder();
    List<LoaderError> errors = new ArrayList<>();
    for (ConfigResource resource : getResources("query/**/*.graphql")) {
      try {
        QueryDefinition queryDefinition = new QueryDefinition(schema);
        // with callback to read defined query parameters from command comment
        queryDefinition.setQuery(resource.asString(new CommandCallbackHandler() {
          @Override
          public void handleCommand(String cmd, Map<String, String> args) {
            if ("query".equals(cmd)) {
              queryDefinition.setQueryName(args.get("name"));
              queryDefinition.setTypeName(args.get("type"));
              queryDefinition.setViewName(args.get("view"));
              queryDefinition.setArgs(args);
            }
          }
        }));
        builder.add(queryDefinition.resolve());
      } catch (Exception e) {
        errors.add(new LoaderError(resource.getName(), resource.getURI(), e.getMessage()));
      }
    }
    if (!errors.isEmpty()) {
      for (LoaderError error : errors) {
        LOG.error(error.toString());
      }
      throw new InvalidQueryDefinition("Queries contain errors", errors);
    }
    return new QueryRegistry(builder.build());
  }
}
