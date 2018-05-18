package com.coremedia.caas.service.media;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.struct.Struct;
import com.coremedia.cap.transform.VariantsStructResolver;

import javax.annotation.Nonnull;
import javax.validation.constraints.Null;

public class ImageVariantsResolver implements VariantsStructResolver {

  private static final String PATH_GLOBAL_SETTINGS = "/Settings/Options/Settings/Responsive Image Settings";

  private static final String DOCTYPE_SETTINGS = "CMSettings";

  private static final String PROPERTY_SETTINGS = "settings";

  private static final String NAME_VARIANTS_STRUCT = "responsiveImageSettings";


  private ContentRepository contentRepository;
  private SettingsService settingsService;


  public ImageVariantsResolver(ContentRepository contentRepository, SettingsService settingsService) {
    this.contentRepository = contentRepository;
    this.settingsService = settingsService;
  }


  @Null
  @Override
  public Struct getVariantsForSite(@Nonnull Site site) {
    Struct setting = settingsService.setting(NAME_VARIANTS_STRUCT, Struct.class, site);
    if (setting == null) {
      Content globalSettings = contentRepository.getChild(PATH_GLOBAL_SETTINGS);
      if (globalSettings != null && globalSettings.getType().isSubtypeOf(DOCTYPE_SETTINGS)) {
        Struct subStruct = globalSettings.getStruct(PROPERTY_SETTINGS);
        if (subStruct.toNestedMaps().containsKey(NAME_VARIANTS_STRUCT)) {
          setting = subStruct.getStruct(NAME_VARIANTS_STRUCT);
        }
      }
    }
    return setting;
  }
}
