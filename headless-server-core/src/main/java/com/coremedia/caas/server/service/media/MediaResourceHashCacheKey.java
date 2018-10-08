package com.coremedia.caas.server.service.media;

import com.coremedia.cache.Cache;
import com.coremedia.cache.CacheKey;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.struct.Struct;
import com.coremedia.cap.transform.TransformImageService;

import com.google.common.hash.HashCode;

import java.security.MessageDigest;
import java.util.Objects;

class MediaResourceHashCacheKey extends CacheKey<String> {

  private Content content;
  private Site site;
  private ImageVariantsResolver imageVariantsResolver;
  private TransformImageService transformImageService;


  MediaResourceHashCacheKey(Content content, Site site, ImageVariantsResolver imageVariantsResolver, TransformImageService transformImageService) {
    this.content = content;
    this.site = site;
    this.imageVariantsResolver = imageVariantsResolver;
    this.transformImageService = transformImageService;
  }


  @Override
  public String evaluate(Cache cache) throws Exception {
    final MessageDigest digest = MessageDigest.getInstance("MD5");
    // add document version id
    if (content.isCheckedIn()) {
      digest.update(content.getCheckedInVersion().getId().getBytes());
    }
    else {
      digest.update(content.getCheckedOutVersion().getId().getBytes());
    }
    if (content.getType().isSubtypeOf("CMPicture")) {
      // add transformation defaults
      digest.update((byte) (transformImageService.isSharpenEnabled() ? 1 : 0));
      digest.update((byte) (transformImageService.isRemoveMetadataEnabled() ? 1 : 0));
      digest.update(Float.valueOf(transformImageService.getDefaultJpegQuality()).toString().getBytes());
      // add transformation rules from settings
      Struct variantsStruct = imageVariantsResolver.getVariantsForSite(site);
      if (variantsStruct != null) {
        digest.update(variantsStruct.toString().getBytes());
      }
    }
    return HashCode.fromBytes(digest.digest()).toString();
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MediaResourceHashCacheKey that = (MediaResourceHashCacheKey) o;
    return Objects.equals(site, that.site) &&
           Objects.equals(content, that.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(site, content);
  }
}
