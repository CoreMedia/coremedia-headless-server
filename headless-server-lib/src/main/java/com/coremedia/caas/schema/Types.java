package com.coremedia.caas.schema;

import graphql.Scalars;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLTypeReference;

public class Types {

  private static final String LIST_PREFIX = "List:";


  public static boolean isList(String typeName) {
    return typeName.startsWith(LIST_PREFIX);
  }


  public static GraphQLOutputType getType(String typeName, boolean isNonNull) {
    GraphQLOutputType baseType = getBaseType(typeName);
    if (isList(typeName)) {
      baseType = new GraphQLList(baseType);
    }
    if (isNonNull) {
      baseType = new GraphQLNonNull(baseType);
    }
    return baseType;
  }


  public static GraphQLOutputType getBaseType(String typeName) {
    String baseTypeName = getBaseTypeName(typeName);
    switch (baseTypeName) {
      case "Boolean":
        return Scalars.GraphQLBoolean;
      case "BigDecimal":
        return Scalars.GraphQLBigDecimal;
      case "BigInteger":
        return Scalars.GraphQLBigInteger;
      case "Byte":
        return Scalars.GraphQLByte;
      case "Char":
        return Scalars.GraphQLChar;
      case "Float":
        return Scalars.GraphQLFloat;
      case "ID":
        return Scalars.GraphQLID;
      case "Integer":
        return Scalars.GraphQLInt;
      case "Long":
        return Scalars.GraphQLLong;
      case "Short":
        return Scalars.GraphQLShort;
      case "String":
        return Scalars.GraphQLString;
      default:
        return new GraphQLTypeReference(baseTypeName);
    }
  }


  public static String getBaseTypeName(String typeName) {
    if (isList(typeName)) {
      return typeName.substring(LIST_PREFIX.length());
    }
    return typeName;
  }
}
