package com.coremedia.caas.query;

import com.coremedia.caas.config.loader.ConfigResourceLoader;
import com.coremedia.caas.config.reader.AbstractConfigReader;
import com.coremedia.caas.schema.SchemaService;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class QueryReader extends AbstractConfigReader {


  public QueryReader(ConfigResourceLoader resourceLoader) {
    super(resourceLoader);
  }


  public QueryRegistry read(SchemaService schema) throws IOException {
    ImmutableList.Builder<QueryDefinition> builder = ImmutableList.builder();
    for (Resource resource : getResources("query/*.graphql")) {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      Map<String, String> queryArgs = new HashMap<>();
      processResource(resource, outputStream, queryArgs);
      builder.add(new QueryDefinition(outputStream.toString(), queryArgs, schema));
    }
    return new QueryRegistry(builder.build());
  }


  private void processResource(Resource resource, ByteArrayOutputStream outputStream, Map<String, String> queryArgs) throws IOException {
    InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      switch (getCmd(line)) {
        case "query":
          queryArgs.putAll(getCmdArgs(line));
          break;
        case "import":
          Map<String, String> args = getCmdArgs(line);
          String file = args.get("file");
          if (!Strings.isNullOrEmpty(file)) {
            Resource includedResource = getResource("query/" + file);
            if (includedResource.exists()) {
              processResource(includedResource, outputStream, queryArgs);
            }
          }
          break;
        default:
          outputStream.write(line.getBytes());
          outputStream.write("\n".getBytes());
          break;
      }
    }
  }
}
