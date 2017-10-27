package com.coremedia.caas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket caas() {
    return new Docket(DocumentationType.SWAGGER_2)
            .groupName("caas")
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(regex("/caas/v1/.*"))
            .build();
  }
}
