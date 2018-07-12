package com.coremedia.caas.schema.field.delegate;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.Types;
import com.coremedia.caas.schema.datafetcher.delegate.BeanDelegatingDataFetcher;
import com.coremedia.caas.schema.datafetcher.delegate.DataFetcherDelegateBean;
import com.coremedia.caas.schema.field.common.AbstractField;

import com.google.common.collect.ImmutableList;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Collection;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

public class BeanDelegatingField extends AbstractField {

  public BeanDelegatingField() {
    super(false, false);
  }


  @Override
  public Collection<GraphQLFieldDefinition> build(SchemaService schemaService) {
    DataFetcherDelegateBean delegateBean = schemaService.getBeanFactory().getBean(getSourceName(), DataFetcherDelegateBean.class);
    return ImmutableList.of(newFieldDefinition()
            .name(getName())
            .type(Types.getType(getTypeName(), isNonNull()))
            .dataFetcherFactory(decorate(new BeanDelegatingDataFetcher(delegateBean, getSourceName(), getFallbackSourceNames(), getTypeName())))
            .build());
  }
}
