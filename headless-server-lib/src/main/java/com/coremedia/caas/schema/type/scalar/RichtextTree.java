package com.coremedia.caas.schema.type.scalar;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;


public class RichtextTree {

  public static final GraphQLScalarType RICHTEXT_TREE = new GraphQLScalarType("RichtextTree", "Built-in richtext object tree", new CoercingRichtextTree<String, Object>());


  private static class CoercingRichtextTree<S, T> implements Coercing<S, Object> {

    @Override
    public Object serialize(Object dataFetcherResult) throws CoercingSerializeException {
      return dataFetcherResult;
    }

    @Override
    public S parseValue(Object input) throws CoercingParseValueException {
      throw new CoercingParseValueException("Parsing unsupported");
    }

    @Override
    public S parseLiteral(Object input) throws CoercingParseLiteralException {
      return null;
    }
  }
}
