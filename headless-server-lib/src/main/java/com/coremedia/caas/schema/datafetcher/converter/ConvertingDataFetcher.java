package com.coremedia.caas.schema.datafetcher.converter;

import com.coremedia.caas.schema.datafetcher.common.AbstractDataFetcher;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ConvertingDataFetcher extends AbstractDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(ConvertingDataFetcher.class);


  private static Class getTargetClass(String typeName, Object value) {
    if (value != null) {
      switch (typeName) {
        case "Boolean":
          return Boolean.class.isAssignableFrom(value.getClass()) ? null : Boolean.class;
        case "BigDecimal":
          return BigDecimal.class.isAssignableFrom(value.getClass()) ? null : BigDecimal.class;
        case "BigInteger":
          return BigInteger.class.isAssignableFrom(value.getClass()) ? null : BigInteger.class;
        case "Byte":
          return Byte.class.isAssignableFrom(value.getClass()) ? null : Byte.class;
        case "Char":
          return Character.class.isAssignableFrom(value.getClass()) ? null : Character.class;
        case "Float":
          return Float.class.isAssignableFrom(value.getClass()) ? null : Float.class;
        case "Integer":
          return Integer.class.isAssignableFrom(value.getClass()) ? null : Integer.class;
        case "Long":
          return Long.class.isAssignableFrom(value.getClass()) ? null : Long.class;
        case "Short":
          return Short.class.isAssignableFrom(value.getClass()) ? null : Short.class;
        case "String":
          return String.class.isAssignableFrom(value.getClass()) ? null : String.class;
      }
    }
    return null;
  }


  protected String typeName;
  protected DataFetcher delegate;


  public ConvertingDataFetcher(String typeName, DataFetcher delegate) {
    this.typeName = typeName;
    this.delegate = delegate;
  }


  @Override
  public Object get(DataFetchingEnvironment environment) {
    Object value = delegate.get(environment);
    if (value != null) {
      Class<?> targetClass = getTargetClass(typeName, value);
      if (targetClass != null) {
        ConversionService conversionService = getContext(environment).getServiceRegistry().getConversionService();
        if (conversionService.canConvert(value.getClass(), targetClass)) {
          return conversionService.convert(value, targetClass);
        } else {
          LOG.error("No conversion possible from {} to {}", value.getClass(), targetClass);
        }
      } else {
        return value;
      }
    }
    return null;
  }
}
