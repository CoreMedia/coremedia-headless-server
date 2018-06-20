package com.coremedia.caas.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
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
            .apiInfo(apiInfo())
            .groupName("caas")
            .select()
            .paths(regex("/caas/v1/.*"))
            .build();
  }


  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
            .title("CoreMedia Content as a Service")
            .description("CoreMedia CaaS API Description")
            .contact(new Contact("CoreMedia AG", "https:/www.coremedia.com", "info@coremedia.com"))
            .license("CoreMedia Open Source License")
            .licenseUrl("https://github.com/CoreMedia/coremedia-headless-server/blob/master/LICENSE.txt")
            .version("1.0.0")
            .build();
  }
}
