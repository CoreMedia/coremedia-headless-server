package com.coremedia.caas.schema.type.scalar;

import com.coremedia.caas.schema.Types;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;


public class RichtextTree {

  public static final GraphQLScalarType CmsRichtextTree = new GraphQLScalarType(Types.RICHTEXT_TREE, "Built-in richtext object tree", new CoercingRichtextTree());


  private static class CoercingRichtextTree implements Coercing<Object, Object> {

    @Override
    public Object serialize(Object dataFetcherResult) throws CoercingSerializeException {
      return dataFetcherResult;
    }

    @Override
    public Object parseValue(Object input) throws CoercingParseValueException {
      throw new CoercingParseValueException("Parsing unsupported");
    }

    @Override
    public Object parseLiteral(Object input) throws CoercingParseLiteralException {
      return null;
    }
  }
}
