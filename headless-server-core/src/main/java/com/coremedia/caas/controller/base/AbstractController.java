package com.coremedia.caas.controller.base;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.config.CaasProcessingDefinition;
import com.coremedia.caas.config.CaasProcessingDefinitionCacheKey;
import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.execution.ExecutionState;
import com.coremedia.caas.services.ServiceRegistry;
import com.coremedia.cache.Cache;
import com.coremedia.cap.common.IdHelper;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.multisite.SitesService;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractController {

  protected static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);

  private static final String TENANT_ID = "tenantId";


  @Autowired
  private Cache cache;

  @Autowired
  protected ContentRepository contentRepository;

  @Autowired
  protected SitesService siteService;

  @Autowired
  @Qualifier("settingsService")
  private SettingsService settingsService;

  @Autowired
  private ServiceRegistry serviceRegistry;

  @Autowired
  @Qualifier("staticProcessingDefinitions")
  private Map<String, CaasProcessingDefinition> staticProcessingDefinitions;

  @Autowired
  private ApplicationContext applicationContext;


  protected List<Site> getSites(String tenantId) {
    return siteService.getSites().stream().filter(site -> tenantId.equals(settingsService.setting(TENANT_ID, String.class, site))).collect(Collectors.toList());
  }

  protected Site getLocalizedSite(String tenantId, String siteId) {
    return getSites(tenantId).stream().filter(item -> siteId.equals(item.getId())).findFirst().orElse(null);
  }


  protected String getPdName(HttpServletRequest request, HttpServletResponse response) {
    return "default";
  }


  protected Content resolveContent(Site localizedSite, String targetId) {
    try {
      Integer id = Integer.parseInt(targetId);
      return contentRepository.getContent(IdHelper.formatContentId(id));
    } catch (NumberFormatException e) {
      Map<String, Content> mappings = settingsService.settingAsMap("caas.mappings", String.class, Content.class, localizedSite.getSiteIndicator());
      return mappings.get(targetId);
    }
  }


  protected Object execute(@NotNull String queryName, @NotNull String viewName, Object target, Map<String, Object> queryArgs, HttpServletRequest request, HttpServletResponse response) {
    String pdName = getPdName(request, response);
    // executable static definition
    CaasProcessingDefinition processingDefinition = staticProcessingDefinitions.get(pdName);
    if (processingDefinition == null || !processingDefinition.hasQueryDefinition(queryName, viewName)) {
      LOG.error("No processing definition found for name '{}' and query '{}#{}'", pdName, queryName, viewName);
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    ExecutionState executionState = new ExecutionState(null);
    // create new proxy for capturing all required runtime services and state
    ExecutionContext context = new ExecutionContext(processingDefinition, executionState, serviceRegistry);
    // run query
    ExecutionInput executionInput = ExecutionInput.newExecutionInput()
            .query(processingDefinition.getQuery(queryName, viewName))
            .root(target)
            .context(context)
            .variables(queryArgs)
            .build();
    ExecutionResult result = GraphQL.newGraphQL(processingDefinition.getQuerySchema(queryName, viewName))
            .preparsedDocumentProvider(processingDefinition.getQueryRegistry())
            .build()
            .execute(executionInput);
    if (!result.getErrors().isEmpty()) {
      for (GraphQLError error : result.getErrors()) {
        LOG.error("GraphQL execution error: {}", error.toString());
      }
    }
    return result.getData();
  }


  protected Object execute(@NotNull String queryName, @NotNull String viewName, @NotNull Site site, Object target, Map<String, Object> queryArgs, HttpServletRequest request, HttpServletResponse response) {
    String pdName = getPdName(request, response);
    // repository defined runtime definition
    CaasProcessingDefinitionCacheKey processingDefinitionCacheKey = new CaasProcessingDefinitionCacheKey(site, applicationContext);
    CaasProcessingDefinition processingDefinition = cache.get(processingDefinitionCacheKey).get(pdName);
    // fallback executable static definition
    if (processingDefinition == null || !processingDefinition.hasQueryDefinition(queryName, viewName)) {
      processingDefinition = staticProcessingDefinitions.get(pdName);
    }
    if (processingDefinition == null || !processingDefinition.hasQueryDefinition(queryName, viewName)) {
      LOG.error("No processing definition found for name '{}' and query '{}#{}'", pdName, queryName, viewName);
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    Content rootDocument = site.getSiteRootDocument();
    if (rootDocument == null) {
      LOG.error("Site has no root document: {}", site);
    }
    ExecutionState executionState = new ExecutionState(rootDocument);
    // create new proxy for capturing all required runtime services and state
    ExecutionContext context = new ExecutionContext(processingDefinition, executionState, serviceRegistry);
    // run query
    ExecutionInput executionInput = ExecutionInput.newExecutionInput()
            .query(processingDefinition.getQuery(queryName, viewName))
            .root(target)
            .context(context)
            .variables(queryArgs)
            .build();
    ExecutionResult result = GraphQL.newGraphQL(processingDefinition.getQuerySchema(queryName, viewName))
            .preparsedDocumentProvider(processingDefinition.getQueryRegistry())
            .build()
            .execute(executionInput);
    if (!result.getErrors().isEmpty()) {
      for (GraphQLError error : result.getErrors()) {
        LOG.error("GraphQL execution error: {}", error.toString());
      }
    }
    return result.getData();
  }
}
