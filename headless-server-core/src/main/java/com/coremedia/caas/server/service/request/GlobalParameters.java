package com.coremedia.caas.server.service.request;

import com.google.common.collect.ImmutableList;

import java.util.List;

public final class GlobalParameters {

  public static final String PREVIEW_DATE = "previewDate";

  public static final List<String> GLOBAL_BLACKLIST = ImmutableList.of(PREVIEW_DATE);


  private GlobalParameters() {
  }
}
