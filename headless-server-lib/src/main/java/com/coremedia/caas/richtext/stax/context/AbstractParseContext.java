package com.coremedia.caas.richtext.stax.context;

import com.coremedia.caas.richtext.stax.config.StaxTransformationConfig;
import com.coremedia.caas.richtext.stax.handler.event.EmptyEventHandler;
import com.coremedia.caas.richtext.stax.handler.event.EventHandler;
import com.google.common.collect.ImmutableList;

import javax.xml.stream.events.StartElement;
import java.util.List;

public abstract class AbstractParseContext implements ParseContext {

  private String name;
  private List<EventHandler> handlers = ImmutableList.of();
  private EventHandler defaultHandler = EmptyEventHandler.instance();


  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public EventHandler getDefaultHandler() {
    return defaultHandler;
  }

  public void setDefaultHandler(EventHandler defaultHandler) {
    this.defaultHandler = defaultHandler;
  }


  public void setHandlers(List<List<EventHandler>> handlers) {
    ImmutableList.Builder<EventHandler> builder = ImmutableList.builder();
    for (List<EventHandler> items : handlers) {
      builder.addAll(items);
    }
    this.handlers = builder.build();
  }


  @Override
  public EventHandler getHandler(StartElement startElement) {
    // ensure handlers are tested in defined order
    for (EventHandler handler : handlers) {
      if (handler.matches(startElement)) {
        return handler;
      }
    }
    return getDefaultHandler();
  }


  @Override
  public <E> void resolveReferences(StaxTransformationConfig config) {
    for (EventHandler handler : handlers) {
      handler.resolve(config);
    }
    getDefaultHandler().resolve(config);
  }
}
