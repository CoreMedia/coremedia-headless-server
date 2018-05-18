package com.coremedia.caas.interceptor;

import com.coremedia.caas.services.request.RequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.coremedia.caas.service.request.ApplicationHeaders.PREVIEW_DATE;
import static com.coremedia.caas.service.request.ContextProperties.REQUEST_DATE;

public class RequestDateInitializer extends HandlerInterceptorAdapter {

  private static final Logger LOG = LoggerFactory.getLogger(RequestDateInitializer.class);


  private boolean isPreview;
  private RequestContext requestContext;


  public RequestDateInitializer(boolean isPreview, RequestContext requestContext) {
    this.isPreview = isPreview;
    this.requestContext = requestContext;
  }


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String previewDate = request.getHeader(PREVIEW_DATE);
    // fail if illegal header is sent
    if (!isPreview && previewDate != null) {
      LOG.warn("Must not pass preview date in live mode");
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return false;
    }
    if (!isPreview || previewDate == null) {
      requestContext.setProperty(REQUEST_DATE, new Date());
    }
    else {
      try {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(previewDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        Date requestDate = Date.from(offsetDateTime.toInstant());
        requestContext.setProperty(REQUEST_DATE, requestDate);
      } catch (DateTimeParseException e) {
        LOG.warn("Cannot parse preview date '{}'", previewDate);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return false;
      }
    }
    return true;
  }
}
