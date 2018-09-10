package com.coremedia.caas.extension.link;

import com.coremedia.caas.config.ProcessingDefinition;
import com.coremedia.caas.query.QueryDefinition;
import com.coremedia.caas.server.controller.interceptor.QueryExecutionInterceptorAdapter;
import com.coremedia.caas.server.controller.media.MediaController;
import com.coremedia.caas.server.service.request.ClientIdentification;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.request.RequestContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Component
public class LinkBuilderSetup extends QueryExecutionInterceptorAdapter {

  public static final String REQUEST_MEDIA_URI_COMPONENTS = "linkBuilder.mediaUC";


  @Override
  public boolean preQuery(String tenantId, String siteId, ClientIdentification clientIdentification, RootContext rootContext, ProcessingDefinition processingDefinition, QueryDefinition queryDefinition, Map<String, Object> queryArgs, ServletWebRequest request) {
    RequestContext requestContext = rootContext.getRequestContext();
    // build prototype URI from either a configured base URI or the current request
    String baseUri = clientIdentification.getOption(requestContext.isPreview() ? "mediaBaseUri_preview" : "mediaBaseUri_live", String.class);
    UriComponentsBuilder prototype = baseUri != null ? UriComponentsBuilder.fromUriString(baseUri) : ServletUriComponentsBuilder.fromContextPath(request.getRequest());
    // create UriComponents with placeholders based on prototype and controller method
    UriComponentsBuilder builder = MvcUriComponentsBuilder.fromMethodCall(
            prototype,
            on(MediaController.class).getMedia(
                    tenantId,
                    siteId,
                    "{contentId}",
                    "{propertyName}",
                    null,
                    null,
                    null,
                    null));
    requestContext.setProperty(REQUEST_MEDIA_URI_COMPONENTS, builder.build());
    return true;
  }
}
