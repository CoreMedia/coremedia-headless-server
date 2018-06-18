package com.coremedia.caas.server;

import com.coremedia.caas.server.interceptor.RequestDateInitializer;
import com.coremedia.caas.server.interceptor.ResponseHeaderInitializer;
import com.coremedia.cache.Cache;
import com.coremedia.cache.CacheCapacityConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import static com.coremedia.caas.server.service.request.ApplicationHeaders.CLIENTID;

@Configuration
@ComponentScan({
        "com.coremedia.caas",
        "com.coremedia.cap.undoc.common.spring"
})
@ImportResource({
        "classpath:/com/coremedia/blueprint/base/settings/impl/bpbase-settings-services.xml",
        "classpath:/com/coremedia/blueprint/base/multisite/bpbase-multisite-services.xml",
        "classpath:/com/coremedia/blueprint/base/pagegrid/impl/bpbase-pagegrid-services.xml",
        "classpath:/com/coremedia/blueprint/base/navigation/context/bpbase-default-contextstrategy.xml"
})
public class CaasConfig extends WebMvcConfigurerAdapter {

  private static final Logger LOG = LoggerFactory.getLogger(CaasConfig.class);


  private RequestDateInitializer requestDateInitializer;
  private ResponseHeaderInitializer responseHeaderInitializer;


  public CaasConfig(RequestDateInitializer requestDateInitializer, ResponseHeaderInitializer responseHeaderInitializer) {
    this.requestDateInitializer = requestDateInitializer;
    this.responseHeaderInitializer = responseHeaderInitializer;
  }


  @Override
  public void configurePathMatch(PathMatchConfigurer matcher) {
    matcher.setUseSuffixPatternMatch(false);
  }


  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/caas/v1/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "OPTIONS")
            .allowedHeaders("Authorization", "Cache-Control", "Content-Type", "X-Requested-With", CLIENTID);
  }


  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(requestDateInitializer).addPathPatterns("/caas/v1/**");
    registry.addInterceptor(responseHeaderInitializer).addPathPatterns("/caas/v1/**");
  }


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/docs/**")
            .addResourceLocations("classpath:/static/docs/");
    super.addResourceHandlers(registry);
  }


  @Bean
  @ConditionalOnProperty("logRequests")
  public Filter logFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter() {
      @Override
      protected boolean shouldLog(HttpServletRequest request) {
        return true;
      }

      @Override
      protected void beforeRequest(HttpServletRequest request, String message) {
        if (!RequestMethod.OPTIONS.name().equals(request.getMethod())) {
          LOG.trace(message);
        }
      }
    };
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(false);
    return filter;
  }


  @Bean
  @ConditionalOnProperty("prettyPrintOutput")
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    return mapper;
  }


  @Bean(name = "cacheCapacityConfigurer", initMethod = "init")
  public CacheCapacityConfigurer configureCache(Cache cache) {
    CacheCapacityConfigurer configurer = new CacheCapacityConfigurer();
    configurer.setCache(cache);
    configurer.setCapacities(ImmutableMap.of(
            "java.lang.Object", 10000L
    ));
    return configurer;
  }
}
