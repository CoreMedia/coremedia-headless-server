package com.coremedia.caas.server.interceptor;

import com.coremedia.caas.service.request.RequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.coremedia.caas.server.service.request.ContextProperties.REQUEST_DATE;
import static com.coremedia.caas.server.service.request.GlobalParameters.PREVIEW_DATE;

public class RequestDateInitializer extends HandlerInterceptorAdapter {

  private static final Logger LOG = LoggerFactory.getLogger(RequestDateInitializer.class);

  public static final String PREVIEW_DATE_FORMAT = "dd-MM-yyyy HH:mm VV";
  public static final DateTimeFormatter PREVIEW_DATE_FORMATTER = DateTimeFormatter.ofPattern(PREVIEW_DATE_FORMAT);


  private boolean isPreview;
  private RequestContext requestContext;


  public RequestDateInitializer(boolean isPreview, RequestContext requestContext) {
    this.isPreview = isPreview;
    this.requestContext = requestContext;
  }


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String previewDate = request.getParameter(PREVIEW_DATE);
    // fail if illegal header is sent
    if (!isPreview && previewDate != null) {
      LOG.warn("Must not pass preview date in live mode");
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return false;
    }
    if (!isPreview || previewDate == null) {
      requestContext.setProperty(REQUEST_DATE, ZonedDateTime.now(ZoneId.systemDefault()));
    }
    else {
      try {
        requestContext.setProperty(REQUEST_DATE, ZonedDateTime.parse(previewDate, PREVIEW_DATE_FORMATTER));
      } catch (DateTimeParseException e) {
        LOG.warn("Cannot parse preview date '{}'", previewDate);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return false;
      }
    }
    return true;
  }
}
