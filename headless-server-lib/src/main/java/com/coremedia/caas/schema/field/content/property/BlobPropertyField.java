package com.coremedia.caas.schema.field.content.property;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.content.property.BlobPropertyDataFetcher;
import com.coremedia.caas.schema.field.common.AbstractField;
import com.coremedia.caas.schema.type.object.ContentBlob;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class BlobPropertyField extends AbstractField {

  public BlobPropertyField() {
    super(false, true);
  }


  @Override
  public Collection<GraphQLFieldDefinition> build(SchemaService schemaService) {
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(ContentBlob.TYPENAME, isNonNull()))
            .dataFetcherFactory(decorate(new BlobPropertyDataFetcher(getSourceName(), getFallbackSourceNames())))
            .build());
  }
}
