package com.coremedia.caas.server.interceptor;

import com.google.common.base.Joiner;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.coremedia.caas.server.service.request.ApplicationHeaders.CLIENTID;

public class ResponseHeaderInitializer extends HandlerInterceptorAdapter {

  private static String[] VARIANT_HEADERS = {CLIENTID};

  private static String VARIANT_HEADER_VALUE = Joiner.on(',').join(VARIANT_HEADERS);


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    response.setHeader("Vary", VARIANT_HEADER_VALUE);
    return true;
  }
}
