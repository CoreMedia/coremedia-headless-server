package com.coremedia.caas.schema.datafetcher.converter;

import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ZonedDateTimeToStringConverter implements DataFetcherConverter<ZonedDateTime, String> {

  @Override
  public String convert(ZonedDateTime zonedDateTime) {
    return DateTimeFormatter.ISO_DATE_TIME.format(zonedDateTime);
  }
}
