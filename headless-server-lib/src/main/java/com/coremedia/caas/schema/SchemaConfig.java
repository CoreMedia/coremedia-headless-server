package com.coremedia.caas.schema;

import com.coremedia.caas.schema.datafetcher.converter.DataFetcherConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;

import java.util.Set;

@Configuration
public class SchemaConfig {

  @Bean
  @Qualifier("dataFetcherConversionService")
  public ConversionService dataFetcherConversionService(Set<DataFetcherConverter> converters) {
    ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
    factory.setConverters(converters);
    factory.afterPropertiesSet();
    return factory.getObject();
  }
}
