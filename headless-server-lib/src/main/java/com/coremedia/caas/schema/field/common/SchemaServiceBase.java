package com.coremedia.caas.schema.field.common;

import com.coremedia.caas.schema.SchemaService;
import com.coremedia.caas.schema.datafetcher.delegate.DataFetcherDelegateBean;
import com.coremedia.caas.service.expression.FieldExpression;
import com.coremedia.caas.service.expression.FieldExpressionCompiler;

import java.util.List;
import java.util.stream.Collectors;

public abstract class SchemaServiceBase {

  protected DataFetcherDelegateBean getDelegateBean(String name, SchemaService schemaService) {
    return schemaService.getBeanFactory().getBean(name, DataFetcherDelegateBean.class);
  }


  protected FieldExpression<?> getExpression(String sourcePath, SchemaService schemaService) {
    final FieldExpressionCompiler compiler = schemaService.getBeanFactory().getBean(FieldExpressionCompiler.class);
    return compiler.compile(sourcePath);
  }

  @SuppressWarnings("WeakerAccess")
  protected List<FieldExpression<?>> getExpressions(List<String> sourcePaths, SchemaService schemaService) {
    final FieldExpressionCompiler compiler = schemaService.getBeanFactory().getBean(FieldExpressionCompiler.class);
    return sourcePaths != null ? sourcePaths.stream().map(compiler::compile).collect(Collectors.toList()) : null;
  }
}
