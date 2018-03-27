package com.coremedia.caas.schema;

import com.coremedia.caas.schema.datafetcher.converter.DataFetcherConverter;
import com.coremedia.caas.schema.type.scalar.MapOfScalars;

import com.google.common.collect.ImmutableSet;
import graphql.schema.GraphQLScalarType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;

import java.util.Set;

@Configuration
public class SchemaConfig {

  public static final String CONVERSION_SERVICE = "dataFetcherConversionService";


  @Bean(CONVERSION_SERVICE)
  public ConversionService dataFetcherConversionService(Set<DataFetcherConverter> converters) {
    ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
    factory.setConverters(converters);
    factory.afterPropertiesSet();
    return factory.getObject();
  }


  @Bean
  public Set<GraphQLScalarType> builtinScalars(@Qualifier(CONVERSION_SERVICE) ConversionService conversionService) {
    ImmutableSet.Builder<GraphQLScalarType> builder = ImmutableSet.builder();
    builder.addAll(new MapOfScalars(conversionService).getTypes().values());
    return builder.build();
  }
}
