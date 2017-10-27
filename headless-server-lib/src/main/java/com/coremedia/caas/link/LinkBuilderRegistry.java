package com.coremedia.caas.link;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class LinkBuilderRegistry {

  private Map<String, LinkBuilder> builderMapping;


  public LinkBuilderRegistry(@NotNull Map<String, LinkBuilder> builderMapping) {
    this.builderMapping = builderMapping;
  }


  public LinkBuilder getBuilder() {
    return getBuilder("default");
  }

  public LinkBuilder getBuilder(String name) {
    return builderMapping.get(name);
  }
}
