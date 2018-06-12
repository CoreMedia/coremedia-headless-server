package com.coremedia.caas.server.resolver;

import com.coremedia.cap.common.IdHelper;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.multisite.Site;

import com.google.common.primitives.Ints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(10)
public class ContentIdResolver implements TargetResolver {

  @Autowired
  private ContentRepository contentRepository;


  @Override
  public Object resolveTarget(Site site, String targetId) {
    Integer id = Ints.tryParse(targetId);
    if (id != null) {
      return contentRepository.getContent(IdHelper.formatContentId(id));
    }
    return null;
  }
}
