package com.coremedia.caas.server;

import com.coremedia.caas.schema.InvalidDefinition;
import com.coremedia.util.Hooks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class CaasServletInitializer extends SpringBootServletInitializer {

  private static final Logger LOG = LoggerFactory.getLogger(CaasServletInitializer.class);


  private static Throwable unwrap(Throwable e) {
    return (e.getCause() != null) ? unwrap(e.getCause()) : e;
  }


  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(CaasServletInitializer.class);
  }

  public static void main(String[] args) {
    try {
      Hooks.enable();
      SpringApplication.run(CaasServletInitializer.class, args);
    } catch (BeanCreationException e) {
      if (BeanCreationException.class.isAssignableFrom(e.getClass())) {
        Throwable c = unwrap(e);
        if (InvalidDefinition.class.isAssignableFrom(c.getClass())) {
          LOG.error("Application startup failed, cause: {}", ((InvalidDefinition) c).getDetailMessage());
          return;
        }
      }
      LOG.error("Application startup failed, cause: {}", e.getMessage());
    } finally {
      Hooks.disable();
    }
  }
}
