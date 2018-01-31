package com.coremedia.caas.config.reader;

import java.util.Map;

public abstract class CommandCallbackHandler {

  public abstract void handleCommand(String cmd, Map<String, String> args);
}
