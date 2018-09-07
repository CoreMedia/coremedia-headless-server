package com.coremedia.caas.server.link;

import com.coremedia.caas.link.LinkBuilder;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.repository.content.BlobProxy;
import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.cap.common.IdHelper;

import org.springframework.stereotype.Component;

@Component("simpleLinkBuilder")
public class SimpleLinkBuilder implements LinkBuilder {

  @Override
  public String createLink(Object target, RootContext rootContext) {
    if (target instanceof ContentProxy) {
      ContentProxy content = (ContentProxy) target;
      if (content.isSubtypeOf("CMImage")) {
        return "coremedia:///image/" + IdHelper.parseContentId(content.getId()) + "/data";
      }
      else if (content.isSubtypeOf("CMPicture")) {
        return "coremedia:///image/" + IdHelper.parseContentId(content.getId()) + "/data";
      }
      else if (content.isSubtypeOf("CMArticle")) {
        return "coremedia:///article/" + content.getString("segment") + "~" + IdHelper.parseContentId(content.getId());
      }
      else if (content.isSubtypeOf("CMNavigation")) {
        return "coremedia:///page/" + content.getString("segment") + "~" + IdHelper.parseContentId(content.getId());
      }
      else if (content.isSubtypeOf("CMExternalLink")) {
        return content.getString("url");
      }
      else if (content.isSubtypeOf("CMDownload")) {
        BlobProxy blob = content.getBlob("data");
        if (blob != null) {
          return "coremedia:///download/" + IdHelper.parseContentId(content.getId()) + "/data";
        }
      }
      else if (content.isSubtypeOf("CMVisual") || content.isSubtypeOf("CMAudio")) {
        BlobProxy blob = content.getBlob("data");
        if (blob != null) {
          return "coremedia:///media/" + IdHelper.parseContentId(content.getId()) + "/data";
        }
        return content.getString("dataUrl");
      }
    }
    else if (target instanceof String) {
      return (String) target;
    }
    return "";
  }
}
