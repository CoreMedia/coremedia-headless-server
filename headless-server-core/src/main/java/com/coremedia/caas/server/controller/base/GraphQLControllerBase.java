package com.coremedia.caas.server.controller.base;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.config.ProcessingDefinition;
import com.coremedia.caas.config.ProcessingDefinitionCacheKey;
import com.coremedia.caas.execution.ExecutionContext;
import com.coremedia.caas.server.service.request.ClientIdentification;
import com.coremedia.caas.service.ServiceRegistry;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.security.AccessControlViolation;
import com.coremedia.cache.Cache;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Collections;
import java.util.Map;
import javax.validation.constraints.NotNull;

public abstract class GraphQLControllerBase extends ControllerBase {

  private static final Logger LOG = LoggerFactory.getLogger(GraphQLControllerBase.class);


  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private Cache cache;

  @Autowired
  private ServiceRegistry serviceRegistry;

  @Autowired
  private SettingsService settingsService;

  @Autowired
  @Qualifier("staticProcessingDefinitions")
  private Map<String, ProcessingDefinition> staticProcessingDefinitions;


  public GraphQLControllerBase(String timerName) {
    super(timerName);
  }


  private Map<String, Object> getQueryArgs(ServletWebRequest request) {
    return Collections.emptyMap();
  }


  private Object runQuery(@NotNull RootContext rootContext, @NotNull ClientIdentification clientIdentification, @NotNull String queryName, @NotNull String viewName, Map<String, Object> queryArgs, ServletWebRequest request) {
    String definitionName = clientIdentification.getDefinitionName();
    // repository defined runtime definition
    ProcessingDefinitionCacheKey processingDefinitionCacheKey = new ProcessingDefinitionCacheKey(rootContext.getSite().getSiteIndicator(), settingsService, applicationContext);
    ProcessingDefinition resolvedDefinition = cache.get(processingDefinitionCacheKey).get(definitionName);
    // fallback executable static definition
    if (resolvedDefinition == null || !resolvedDefinition.hasQueryDefinition(queryName, viewName)) {
      resolvedDefinition = staticProcessingDefinitions.get(definitionName);
    }
    if (resolvedDefinition == null || !resolvedDefinition.hasQueryDefinition(queryName, viewName)) {
      LOG.error("No processing definition found for name '{}' and query '{}#{}'", definitionName, queryName, viewName);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    ProcessingDefinition processingDefinition = resolvedDefinition;
    // create new runtime context for capturing all required runtime services and state
    ExecutionContext context = new ExecutionContext(processingDefinition, serviceRegistry, rootContext);
    // run query
    ExecutionInput executionInput = ExecutionInput.newExecutionInput()
            .query(processingDefinition.getQuery(queryName, viewName))
            .root(rootContext.getTarget())
            .context(context)
            .variables(queryArgs)
            .build();
    ExecutionResult result = GraphQL.newGraphQL(processingDefinition.getQuerySchema(rootContext.getTarget(), queryName, viewName))
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


  protected Object execute(String tenantId, String siteId, String queryName, String targetId, String viewName, ServletWebRequest request) {
    try {
      RootContext rootContext;
      if (targetId == null) {
        rootContext = resolveRootContext(tenantId, siteId, request);
      }
      else {
        rootContext = resolveRootContext(tenantId, siteId, targetId, request);
      }
      // determine client
      ClientIdentification clientIdentification = resolveClient(rootContext, request);
      String clientId = clientIdentification.getId().toString();
      String definitionName = clientIdentification.getDefinitionName();
      // determine query arguments
      Map<String, Object> queryArgs = getQueryArgs(request);
      // initialize expression evaluator
      serviceRegistry.getExpressionEvaluator().init(queryArgs);
      // run query
      return execute(() -> runQuery(rootContext, clientIdentification, queryName, viewName, queryArgs, request), "tenant", tenantId, "site", siteId, "client", clientId, "pd", definitionName, "query", queryName, "view", viewName);
    } catch (AccessControlViolation e) {
      return handleError(e, request);
    } catch (ResponseStatusException e) {
      return handleError(e, request);
    } catch (Exception e) {
      return handleError(e, request);
    }
  }
}
