package com.coremedia.caas.query;

import com.coremedia.caas.config.loader.ConfigResourceLoader;
import com.coremedia.caas.config.reader.AbstractConfigReader;
import com.coremedia.caas.config.reader.CommandCallbackHandler;
import com.coremedia.caas.config.reader.ConfigResource;
import com.coremedia.caas.schema.InvalidQueryDefinition;
import com.coremedia.caas.schema.SchemaService;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.util.Map;

public class QueryReader extends AbstractConfigReader {

  public QueryReader(ConfigResourceLoader resourceLoader) {
    super(resourceLoader);
  }


  public QueryRegistry read(SchemaService schema) throws InvalidQueryDefinition, IOException {
    ImmutableList.Builder<QueryDefinition> builder = ImmutableList.builder();
    for (ConfigResource resource : getResources("query/**/*.graphql")) {
      QueryDefinition queryDefinition = new QueryDefinition(schema);
      // with callback to read defined query parameters from command comment
      queryDefinition.setQuery(resource.asString(new CommandCallbackHandler() {
        @Override
        public void handleCommand(String cmd, Map<String, String> args) {
          if ("query".equals(cmd)) {
            queryDefinition.setQueryName(args.get("name"));
            queryDefinition.setTypeName(args.get("type"));
            queryDefinition.setViewName(args.get("view"));
          }
        }
      }));
      builder.add(queryDefinition.resolve());
    }
    return new QueryRegistry(builder.build());
  }
}
