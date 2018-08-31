package com.coremedia.caas.schema.datafetcher.converter;

import org.springframework.stereotype.Component;

@Component
public class IntegerToBooleanConverter implements DataFetcherConverter<Integer, Boolean> {

  @Override
  public Boolean convert(Integer source) {
    return source != 0;
  }
}
