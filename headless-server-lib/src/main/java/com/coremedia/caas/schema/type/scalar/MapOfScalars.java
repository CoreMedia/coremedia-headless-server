package com.coremedia.caas.schema.type.scalar;

import com.coremedia.caas.schema.Types;
import com.google.common.collect.ImmutableMap;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.GraphQLScalarType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;

import java.util.HashMap;
import java.util.Map;

public class MapOfScalars {

  private static final Logger LOG = LoggerFactory.getLogger(MapOfScalars.class);


  private static ConversionService conversionService;

  private static Map<String, GraphQLScalarType> typeMap;

  public static GraphQLScalarType BOOLEAN = graphQLObjectScalar("MapOfBoolean", new CoercingMap<String, Boolean>(Boolean.class));
  public static GraphQLScalarType FLOAT = graphQLObjectScalar("MapOfFloat", new CoercingMap<String, Float>(Float.class));
  public static GraphQLScalarType INTEGER = graphQLObjectScalar("MapOfInteger", new CoercingMap<String, Integer>(Integer.class));
  public static GraphQLScalarType LONG = graphQLObjectScalar("MapOfLong", new CoercingMap<String, Long>(Long.class));
  public static GraphQLScalarType SHORT = graphQLObjectScalar("MapOfShort", new CoercingMap<String, Short>(Short.class));
  public static GraphQLScalarType STRING = graphQLObjectScalar("MapOfString", new CoercingMap<String, String>(String.class));

  static {
    ImmutableMap.Builder<String, GraphQLScalarType> builder = ImmutableMap.builder();
    builder.put(Types.BOOLEAN, BOOLEAN);
    builder.put(Types.FLOAT, FLOAT);
    builder.put(Types.INTEGER, INTEGER);
    builder.put(Types.LONG, LONG);
    builder.put(Types.SHORT, SHORT);
    builder.put(Types.STRING, STRING);
    typeMap = builder.build();
  }


  private static GraphQLScalarType graphQLObjectScalar(String name, CoercingMap<?, ?> coercing) {
    return new GraphQLScalarType(name, "Built-in map of scalar type", coercing);
  }

  public static GraphQLScalarType getType(String baseTypeName) {
    return typeMap.get(baseTypeName);
  }


  public MapOfScalars(ConversionService conversionService) {
    MapOfScalars.conversionService = conversionService;
  }


  public Map<String, GraphQLScalarType> getTypes() {
    return typeMap;
  }


  private static class CoercingMap<S, T> implements Coercing<S, Map<String, T>> {

    private Class<T> targetClass;

    private CoercingMap(Class<T> targetClass) {
      this.targetClass = targetClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, T> serialize(Object dataFetcherResult) {
      return ((Map<String, Object>) dataFetcherResult).entrySet().stream()
              .filter(e -> {
                Object v = e.getValue();
                return v != null && conversionService.canConvert(v.getClass(), targetClass);
              })
              .collect(HashMap<String, T>::new, (m, e) -> {
                try {
                  m.put(e.getKey(), conversionService.convert(e.getValue(), targetClass));
                } catch (Exception ex) {
                  LOG.warn("Type conversion failed for {}", e);
                }
              }, HashMap::putAll);
    }

    @Override
    public S parseValue(Object input) {
      throw new CoercingParseValueException("Parsing unsupported");
    }

    @Override
    public S parseLiteral(Object input) {
      return null;
    }
  }
}
