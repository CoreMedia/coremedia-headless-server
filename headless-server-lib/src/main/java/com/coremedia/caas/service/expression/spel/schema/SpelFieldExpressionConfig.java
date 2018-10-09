package com.coremedia.caas.service.expression.spel.schema;

import com.coremedia.caas.schema.datafetcher.content.util.MapStruct;
import com.coremedia.caas.schema.datafetcher.content.util.Richtext;
import com.coremedia.caas.service.expression.FieldExpressionCompiler;
import com.coremedia.caas.service.expression.spel.ReadOnlyMapAccessor;
import com.coremedia.caas.service.repository.content.ContentProxyPropertyAccessor;
import com.coremedia.caas.service.repository.content.StructProxyPropertyAccessor;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.List;

@Configuration
public class SpelFieldExpressionConfig {

  @Bean("schemaEvaluationContext")
  public EvaluationContext schemaEvaluationContext(@Qualifier("schemaContentModelMethodResolver") MethodResolver contentMethodResolver) {
    List<PropertyAccessor> propertyAccessors = ImmutableList.of(
            new ContentProxyPropertyAccessor(),
            new StructProxyPropertyAccessor(),
            new ReadOnlyMapAccessor(),
            new ReflectivePropertyAccessor());
    // customize evaluation context
    StandardEvaluationContext context = new StandardEvaluationContext();
    context.setPropertyAccessors(propertyAccessors);
    context.addMethodResolver(contentMethodResolver);
    // add allowed custom wrapper types
    context.setTypeLocator((typeName) -> {
      if (Richtext.NAME.equals(typeName)) {
        return Richtext.class;
      }
      else if (MapStruct.NAME.equals(typeName)) {
        return MapStruct.class;
      }
      throw new EvaluationException("Type " + typeName + " not allowed");
    });
    return context;
  }

  @Bean("schemaFieldExpressionCompiler")
  public FieldExpressionCompiler fieldExpressionCompiler(@Qualifier("schemaEvaluationContext") EvaluationContext evaluationContext) {
    return new SpelFieldExpressionService(evaluationContext);
  }
}
