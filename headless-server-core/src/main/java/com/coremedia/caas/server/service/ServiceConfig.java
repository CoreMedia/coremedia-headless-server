package com.coremedia.caas.server.service;

import com.coremedia.caas.server.CaasServiceConfig;
import com.coremedia.caas.server.service.expression.spel.SpelExpressionEvaluator;
import com.coremedia.caas.server.service.request.DefaultRequestContext;
import com.coremedia.caas.service.ServiceRegistry;
import com.coremedia.caas.service.cache.CacheInstances;
import com.coremedia.caas.service.cache.Weighted;
import com.coremedia.caas.service.expression.ExpressionEvaluator;
import com.coremedia.caas.service.repository.content.ContentProxyPropertyAccessor;
import com.coremedia.caas.service.request.RequestContext;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Weigher;
import com.google.common.collect.ImmutableList;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.expression.MapAccessor;
import org.springframework.core.convert.ConversionService;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Configuration
public class ServiceConfig {

  private CaasServiceConfig serviceConfig;

  public ServiceConfig(CaasServiceConfig caasServiceConfig) {
    this.serviceConfig = caasServiceConfig;
  }


  @Bean("modelMapper")
  @ConditionalOnMissingBean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
            .setFieldMatchingEnabled(true)
            .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
    return modelMapper;
  }


  @Bean("requestContext")
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
  public RequestContext createRequestContext() {
    return new DefaultRequestContext();
  }


  @Bean("spelEvaluator")
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
  public ExpressionEvaluator createSpelExpressionEvaluator(ContentProxyPropertyAccessor contentPropertyAccessor, @Qualifier("queryContentModelMethodResolver") MethodResolver contentMethodResolver) {
    List<PropertyAccessor> propertyAccessors = ImmutableList.of(
            contentPropertyAccessor,
            new MapAccessor(),
            new ReflectivePropertyAccessor());
    // customize evaluation context
    StandardEvaluationContext context = new StandardEvaluationContext();
    context.setPropertyAccessors(propertyAccessors);
    context.addMethodResolver(contentMethodResolver);
    return new SpelExpressionEvaluator(context);
  }


  @Bean("cacheManager")
  @SuppressWarnings("unchecked")
  public CacheManager cacheManager(MeterRegistry registry) {
    ImmutableList.Builder<org.springframework.cache.Cache> builder = ImmutableList.builder();
    // richtext cache
    if (serviceConfig.getCacheSpecs().containsKey(CacheInstances.RICHTEXT)) {
      Cache cache = Caffeine.from(serviceConfig.getCacheSpecs().get(CacheInstances.RICHTEXT))
              .weigher((Weigher<Object, Weighted>) (key, value) -> value.getWeight())
              .build();
      CaffeineCacheMetrics.monitor(registry, cache, CacheInstances.RICHTEXT);
      builder.add(new CaffeineCache(CacheInstances.RICHTEXT, cache));
    }
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    cacheManager.setCaches(builder.build());
    return cacheManager;
  }


  @Bean
  public ServiceRegistry createServiceRegistry(
          @Qualifier("cacheManager") CacheManager cacheManager,
          @Qualifier("dataFetcherConversionService") ConversionService conversionService,
          @Qualifier("spelEvaluator") ExpressionEvaluator expressionEvaluator) {
    return new ServiceRegistryImpl(cacheManager, conversionService, expressionEvaluator);
  }
}
