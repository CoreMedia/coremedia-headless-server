package com.coremedia.caas.schema.type;

import com.coremedia.caas.schema.Types;
import com.google.common.collect.ImmutableMap;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.GraphQLScalarType;
import org.springframework.core.convert.ConversionService;

import java.util.HashMap;
import java.util.Map;

public class MapOfScalars {

  private static ConversionService conversionService;


  private static Map<String, GraphQLScalarType> typeMap;

  static {
    ImmutableMap.Builder<String, GraphQLScalarType> builder = ImmutableMap.builder();
    builder.put(Types.BOOLEAN, graphQLObjectScalar("MapOfBoolean", new CoercingMap<String, Boolean>(Boolean.class)));
    builder.put(Types.FLOAT, graphQLObjectScalar("MapOfFloat", new CoercingMap<String, Float>(Float.class)));
    builder.put(Types.INTEGER, graphQLObjectScalar("MapOfInteger", new CoercingMap<String, Integer>(Integer.class)));
    builder.put(Types.LONG, graphQLObjectScalar("MapOfLong", new CoercingMap<String, Long>(Long.class)));
    builder.put(Types.SHORT, graphQLObjectScalar("MapOfShort", new CoercingMap<String, Short>(Short.class)));
    builder.put(Types.STRING, graphQLObjectScalar("MapOfString", new CoercingMap<String, String>(String.class)));
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
