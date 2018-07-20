package com.coremedia.caas.schema.type.field;

import com.coremedia.caas.schema.FieldBuilder;
import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.StaticDataFetcherFactory;
import com.coremedia.caas.schema.datafetcher.common.LiteralDataFetcher;

import com.google.common.collect.ImmutableList;
import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class LiteralFields implements FieldBuilder {

  public static final String ARGUMENT_VALUE = "value";


  @Override
  public String getName() {
    return "literals";
  }


  @Override
  public Collection<GraphQLFieldDefinition> build(SchemaService schemaService) {
    return ImmutableList.of(
            newFieldDefinition()
                    .name("lstring")
                    .type(Types.getType(Types.STRING, true))
                    .argument(newArgument().name(ARGUMENT_VALUE).type(Scalars.GraphQLString))
                    .dataFetcherFactory(new StaticDataFetcherFactory(new LiteralDataFetcher()))
                    .build(),
            newFieldDefinition()
                    .name("lint")
                    .type(Types.getType(Types.INT, true))
                    .argument(newArgument().name(ARGUMENT_VALUE).type(Scalars.GraphQLInt))
                    .dataFetcherFactory(new StaticDataFetcherFactory(new LiteralDataFetcher()))
                    .build());
  }
}
