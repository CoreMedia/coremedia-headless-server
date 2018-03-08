package com.coremedia.caas.schema.datafetcher.converter;

import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.common.AbstractDataFetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.validation.constraints.NotNull;

public class ConvertingDataFetcher extends AbstractDataFetcher {

  private static final Logger LOG = LoggerFactory.getLogger(ConvertingDataFetcher.class);


  private static Class getTargetClass(String typeName, Object value) {
    if (value != null) {
      switch (typeName) {
        case Types.BOOLEAN:
          return Boolean.class.isAssignableFrom(value.getClass()) ? null : Boolean.class;
        case Types.BIGDECIMAL:
          return BigDecimal.class.isAssignableFrom(value.getClass()) ? null : BigDecimal.class;
        case Types.BIGINTEGER:
          return BigInteger.class.isAssignableFrom(value.getClass()) ? null : BigInteger.class;
        case Types.BYTE:
          return Byte.class.isAssignableFrom(value.getClass()) ? null : Byte.class;
        case Types.CHAR:
          return Character.class.isAssignableFrom(value.getClass()) ? null : Character.class;
        case Types.FLOAT:
          return Float.class.isAssignableFrom(value.getClass()) ? null : Float.class;
        case Types.ID:
          return String.class.isAssignableFrom(value.getClass()) ? null : String.class;
        case Types.INT:
          return Integer.class.isAssignableFrom(value.getClass()) ? null : Integer.class;
        case Types.LONG:
          return Long.class.isAssignableFrom(value.getClass()) ? null : Long.class;
        case Types.SHORT:
          return Short.class.isAssignableFrom(value.getClass()) ? null : Short.class;
        case Types.STRING:
          return String.class.isAssignableFrom(value.getClass()) ? null : String.class;
      }
    }
    return null;
  }


  protected String typeName;
  protected DataFetcher delegate;


  public ConvertingDataFetcher(@NotNull String typeName, @NotNull DataFetcher delegate) {
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
