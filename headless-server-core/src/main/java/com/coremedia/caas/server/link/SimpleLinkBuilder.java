package com.coremedia.caas.server.link;

import com.coremedia.caas.link.LinkBuilder;
import com.coremedia.caas.server.CaasServiceConfig;
import com.coremedia.caas.server.service.media.MediaResourceModel;
import com.coremedia.caas.server.service.media.MediaResourceModelFactory;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.repository.content.BlobProxy;
import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.cap.common.IdHelper;

import org.springframework.stereotype.Component;

@Component("simpleLinkBuilder")
public class SimpleLinkBuilder implements LinkBuilder<String> {

  public static final String EMPTY_LINK = "";


  private CaasServiceConfig serviceConfig;


  public SimpleLinkBuilder(CaasServiceConfig serviceConfig) {
    this.serviceConfig = serviceConfig;
  }


  @Override
  public String createLink(Object target, RootContext rootContext) {
    if (target instanceof ContentProxy) {
      ContentProxy content = (ContentProxy) target;
      if (content.isSubtypeOf("CMImage")) {
        return createBinaryLink(content, "image", "data", rootContext);
      }
      else if (content.isSubtypeOf("CMPicture")) {
        return createBinaryLink(content, "image", "data", rootContext);
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
        return createBinaryLink(content, "download", "data", rootContext);
      }
      else if (content.isSubtypeOf("CMVisual") || content.isSubtypeOf("CMAudio")) {
        String link = createBinaryLink(content, "media", "data", rootContext);
        if (EMPTY_LINK.equals(link)) {
          link = content.getString("dataUrl");
        }
        return link;
      }
    }
    else if (target instanceof String) {
      return (String) target;
    }
    return EMPTY_LINK;
  }


  private String createBinaryLink(ContentProxy content, String typeName, String propertyName, RootContext rootContext) {
    BlobProxy blob = content.getBlob(propertyName);
    if (blob != null && !blob.isEmpty()) {
      Integer mediaId = IdHelper.parseContentId(content.getId());
      if (serviceConfig.isBinaryUriHashesEnabled()) {
        // fetch hash from media model
        MediaResourceModel model = rootContext.getModelFactory().createModel(MediaResourceModelFactory.MODEL_NAME, content, propertyName);
        String mediaHash = model.getHash();
        // use custom URI format
        return "coremedia:///" + typeName + "/" + mediaId + "/" + propertyName + "/" + mediaHash;
      }
      else {
        return "coremedia:///" + typeName + "/" + mediaId + "/" + propertyName;
      }
    }
    return EMPTY_LINK;
  }
}
