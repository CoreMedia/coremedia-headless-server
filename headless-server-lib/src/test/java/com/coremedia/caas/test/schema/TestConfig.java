package com.coremedia.caas.test.schema;

import com.coremedia.caas.link.LinkBuilder;
import com.coremedia.caas.service.expression.FieldExpressionCompiler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        "com.coremedia.caas.schema"
})
public class TestConfig {

  @Bean
  public FieldExpressionCompiler fieldExpressionCompiler() {
    return (pathExpression) -> null;
  }


  @Bean("testLinkBuilder")
  public LinkBuilder testLinkBuilder() {
    return (target, rootContext) -> "";
  }
}
