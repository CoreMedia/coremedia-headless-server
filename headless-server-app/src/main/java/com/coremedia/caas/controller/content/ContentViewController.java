package com.coremedia.caas.controller.content;

import com.coremedia.caas.controller.base.AbstractController;
import com.coremedia.caas.monitoring.Metrics;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.multisite.Site;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.OPTIONS}, allowedHeaders = {"authorization", "content-type"})
@RequestMapping("/caas/v1/{tenantId}/sites/{siteId}")
public class ContentViewController extends AbstractController {

  private static final String TIMER_NAME = "caas.content.timer";


  @Autowired
  private Metrics metrics;


  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Object getContent(@PathVariable String tenantId, @PathVariable String siteId, HttpServletRequest request, HttpServletResponse response) {
    return executeQuery(tenantId, siteId, "default", request, response);
  }

  @RequestMapping(value = "/{viewName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Object getContent(@PathVariable String tenantId, @PathVariable String siteId, @PathVariable String viewName, HttpServletRequest request, HttpServletResponse response) {
    return executeQuery(tenantId, siteId, viewName, request, response);
  }

  @RequestMapping(value = "/{queryName}/{targetId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Object getContent(@PathVariable String tenantId, @PathVariable String siteId, @PathVariable String queryName, @PathVariable String targetId, HttpServletRequest request, HttpServletResponse response) {
    return executeQuery(tenantId, siteId, queryName, targetId, "default", request, response);
  }

  @RequestMapping(value = "/{queryName}/{targetId}/{viewName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Object getContent(@PathVariable String tenantId, @PathVariable String siteId, @PathVariable String queryName, @PathVariable String targetId, @PathVariable String viewName, HttpServletRequest request, HttpServletResponse response) {
    return executeQuery(tenantId, siteId, queryName, targetId, viewName, request, response);
  }


  private Object executeQuery(String tenantId, String siteId, String viewName, HttpServletRequest request, HttpServletResponse response) {
    Site localizedSite = getLocalizedSite(tenantId, siteId);
    if (localizedSite == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    Content content = localizedSite.getSiteIndicator();
    return metrics.timer(() -> execute("sites", viewName, localizedSite, content, ImmutableMap.of(), request, response), TIMER_NAME, "tenant", tenantId, "site", siteId, "pd", "default", "query", "sites", "view", viewName);
  }


  private Object executeQuery(String tenantId, String siteId, String queryName, String targetId, String viewName, HttpServletRequest request, HttpServletResponse response) {
    Site localizedSite = getLocalizedSite(tenantId, siteId);
    if (localizedSite == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    Content content = resolveContent(localizedSite, targetId);
    if (content == null || !siteService.isContentInSite(localizedSite, content)) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    return metrics.timer(() -> execute(queryName, viewName, localizedSite, content, ImmutableMap.of(), request, response), TIMER_NAME, "tenant", tenantId, "site", siteId, "pd", "default", "query", queryName, "view", viewName);
  }
}
