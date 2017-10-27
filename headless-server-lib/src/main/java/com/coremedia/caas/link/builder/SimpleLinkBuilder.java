package com.coremedia.caas.link.builder;

import com.coremedia.caas.link.LinkBuilder;
import com.coremedia.cap.common.CapBlobRef;
import com.coremedia.cap.common.IdHelper;
import com.coremedia.cap.content.Content;
import org.springframework.stereotype.Component;

@Component(SimpleLinkBuilder.NAME)
public class SimpleLinkBuilder implements LinkBuilder {

  public static final String NAME = "SimpleLinkBuilder";


  @Override
  public String createLink(Object target) {
    if (target instanceof CapBlobRef) {
      target = ((CapBlobRef) target).getCapObject();
    }
    if (target instanceof Content) {
      Content content = (Content) target;

      if (content.getType().isSubtypeOf("CMImage")) {
        return "coremedia:///image/" + IdHelper.parseContentId(content.getId()) + "/data";
      } else if (content.getType().isSubtypeOf("CMPicture")) {
        return "coremedia:///image/" + IdHelper.parseContentId(content.getId()) + "/data";
      } else if (content.getType().isSubtypeOf("CMArticle")) {
        return "coremedia:///article/" + content.getString("segment") + "~" + IdHelper.parseContentId(content.getId());
      } else if (content.getType().isSubtypeOf("CMNavigation")) {
        return "coremedia:///page/" + content.getString("segment") + "~" + IdHelper.parseContentId(content.getId());
      } else if (content.getType().isSubtypeOf("CMExternalLink")) {
        return content.getString("url");
      }
    } else if (target instanceof String) {
      return (String) target;
    }
    return "";
  }
}
