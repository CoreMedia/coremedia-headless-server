package com.coremedia.caas.schema;

import com.coremedia.caas.schema.type.MapOfScalars;
import graphql.Scalars;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLTypeReference;

public class Types {

  private static final String LIST_PREFIX = "List:";
  private static final String MAP_PREFIX = "Map:";

  public static final String BOOLEAN = "Boolean";
  public static final String BIGDECIMAL = "BigDecimal";
  public static final String BIGINTEGER = "BigInteger";
  public static final String BYTE = "Byte";
  public static final String CHAR = "Char";
  public static final String FLOAT = "Float";
  public static final String ID = "ID";
  public static final String INTEGER = "Integer";
  public static final String LONG = "Long";
  public static final String SHORT = "Short";
  public static final String STRING = "String";


  public static boolean isList(String typeName) {
    return typeName.startsWith(LIST_PREFIX);
  }

  public static boolean isMap(String typeName) {
    return typeName.startsWith(MAP_PREFIX);
  }


  public static String getBaseTypeName(String typeName) {
    if (isList(typeName)) {
      return typeName.substring(LIST_PREFIX.length());
    } else if (isMap(typeName)) {
      return typeName.substring(MAP_PREFIX.length());
    }
    return typeName;
  }


  public static GraphQLOutputType getType(String typeName, boolean isNonNull) {
    if (isMap(typeName)) {
      GraphQLScalarType baseType = MapOfScalars.getType(getBaseTypeName(typeName));
      return isNonNull ? new GraphQLNonNull(baseType) : baseType;
    } else {
      GraphQLOutputType baseType = getBaseType(getBaseTypeName(typeName));
      if (isList(typeName)) {
        baseType = new GraphQLList(baseType);
      }
      return isNonNull ? new GraphQLNonNull(baseType) : baseType;
    }
  }


  private static GraphQLOutputType getBaseType(String baseTypeName) {
    switch (baseTypeName) {
      case BOOLEAN:
        return Scalars.GraphQLBoolean;
      case BIGDECIMAL:
        return Scalars.GraphQLBigDecimal;
      case BIGINTEGER:
        return Scalars.GraphQLBigInteger;
      case BYTE:
        return Scalars.GraphQLByte;
      case CHAR:
        return Scalars.GraphQLChar;
      case FLOAT:
        return Scalars.GraphQLFloat;
      case ID:
        return Scalars.GraphQLID;
      case INTEGER:
        return Scalars.GraphQLInt;
      case LONG:
        return Scalars.GraphQLLong;
      case SHORT:
        return Scalars.GraphQLShort;
      case STRING:
        return Scalars.GraphQLString;
      default:
        return new GraphQLTypeReference(baseTypeName);
    }
  }
}
