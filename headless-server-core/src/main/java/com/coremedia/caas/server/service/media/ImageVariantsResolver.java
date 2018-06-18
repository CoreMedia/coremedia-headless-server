package com.coremedia.caas.server.service.media;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.struct.Struct;
import com.coremedia.cap.transform.VariantsStructResolver;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.primitives.Ints;
import org.modelmapper.ModelMapper;

import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class ImageVariantsResolver implements VariantsStructResolver {

  private static final String PATH_GLOBAL_SETTINGS = "/Settings/Options/Settings/Responsive Image Settings";

  private static final String DOCTYPE_SETTINGS = "CMSettings";

  private static final String PROPERTY_SETTINGS = "settings";

  private static final String NAME_VARIANTS_STRUCT = "responsiveImageSettings";


  private ContentRepository contentRepository;
  private SettingsService settingsService;
  private ModelMapper modelMapper;


  public ImageVariantsResolver(ContentRepository contentRepository, SettingsService settingsService, ModelMapper modelMapper) {
    this.contentRepository = contentRepository;
    this.settingsService = settingsService;
    this.modelMapper = modelMapper;
  }


  @Null
  @Override
  public Struct getVariantsForSite(@NotNull Site site) {
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


  @NotNull
  public ImageVariantsDescriptor getVariantsDescriptor(@NotNull Site site) {
    ImmutableSortedMap.Builder<String, ImageVariantsDescriptor.Ratio> ratiosBuilder = ImmutableSortedMap.naturalOrder();
    // scan setting for ratio definitions
    Struct setting = getVariantsForSite(site);
    if (setting != null) {
      for (Map.Entry<String, Object> settingEntry : setting.getProperties().entrySet()) {
        Object value = settingEntry.getValue();
        // assume a struct value always defines a ratio
        if (value instanceof Struct) {
          Map<String, Object> subStruct = ((Struct) value).getProperties();
          // map default ratio properties
          ImageVariantsDescriptor.Ratio ratio = modelMapper.map(subStruct, ImageVariantsDescriptor.Ratio.class);
          // scan for defined resolutions by numbered keys
          ImmutableList.Builder<ImageVariantsDescriptor.Dimension> dimensionsBuilder = ImmutableList.builder();
          for (Map.Entry<String, Object> subStructEntry : subStruct.entrySet()) {
            String subKey = subStructEntry.getKey();
            Object subValue = subStructEntry.getValue();
            if (subValue instanceof Struct && Ints.tryParse(subKey) != null) {
              Struct dimensionStruct = (Struct) subValue;
              dimensionsBuilder.add(new ImageVariantsDescriptor.Dimension(dimensionStruct.getInt("width"), dimensionStruct.getInt("height")));
            }
          }
          ratio.setDimensions(dimensionsBuilder.build());
          ratiosBuilder.put(settingEntry.getKey(), ratio);
        }
      }
    }
    return new ImageVariantsDescriptor(ratiosBuilder.build());
  }
}
