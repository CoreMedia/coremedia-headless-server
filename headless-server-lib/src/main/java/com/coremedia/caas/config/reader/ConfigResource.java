package com.coremedia.caas.config.reader;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;

public class ConfigResource {

  private static final String COMMAND_PREFIX = "#!";

  private static final Splitter.MapSplitter COMMAND_SPLITTER = Splitter.on(' ').trimResults().omitEmptyStrings().withKeyValueSeparator('=');

  private static String getCmd(String line) {
    if (line.startsWith(COMMAND_PREFIX)) {
      int argIndex = line.indexOf(' ');
      if (argIndex > 0) {
        return line.substring(COMMAND_PREFIX.length(), argIndex);
      }
    }
    return "";
  }

  private static Map<String, String> getCmdArgs(String line) {
    int argIndex = line.indexOf(' ');
    if (argIndex > 0) {
      return COMMAND_SPLITTER.split(line.substring(argIndex));
    }
    return ImmutableMap.of();
  }


  private Resource resource;


  ConfigResource(Resource resource) {
    this.resource = resource;
  }


  public String getName() {
    return resource.getFilename();
  }

  public URI getURI() {
    try {
      return resource.getURI();
    } catch (IOException e) {
      return null;
    }
  }


  public byte[] asBytes() throws IOException {
    return asBytes(null);
  }

  public byte[] asBytes(CommandCallbackHandler callbackHandler) throws IOException {
    return read(callbackHandler).toByteArray();
  }

  public String asString() throws IOException {
    return asString(null);
  }

  public String asString(CommandCallbackHandler callbackHandler) throws IOException {
    return read(callbackHandler).toString();
  }


  private ByteArrayOutputStream read(CommandCallbackHandler callbackHandler) throws IOException {
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String cmd = getCmd(line);
        if (!cmd.isEmpty()) {
          Map<String, String> args = getCmdArgs(line);
          // fixed import command
          if ("import".equals(cmd)) {
            String file = args.get("file");
            if (!Strings.isNullOrEmpty(file)) {
              Resource includedResource = resource.createRelative(file);
              if (includedResource.exists()) {
                outputStream.write(new ConfigResource(includedResource).asBytes());
              }
              else {
                throw new IOException("File '" + file + "' does not exist");
              }
            }
            else {
              throw new IOException("No filename provided");
            }
          }
          else if (callbackHandler != null) {
            callbackHandler.handleCommand(cmd, args);
          }
        }
        else {
          outputStream.write(line.getBytes());
          outputStream.write("\n".getBytes());
        }
      }
      return outputStream;
    }
  }
}
