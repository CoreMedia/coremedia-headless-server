package com.coremedia.caas.server.service.media;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.server.service.media.ImageVariantsDescriptor.Dimension;
import com.coremedia.caas.server.service.media.ImageVariantsDescriptor.Ratio;
import com.coremedia.cap.common.CapPropertyDescriptor;
import com.coremedia.cap.common.CapPropertyDescriptorType;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.struct.Struct;
import com.coremedia.cap.transform.VariantsStructResolver;

import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
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
    Struct variantSetting = getVariantsForSite(site);
    if (variantSetting != null) {
      // scan setting for ratio definitions
      Map<String, Ratio> ratios = variantSetting.getType().getDescriptors().stream()
              .filter(rd -> rd.getType() == CapPropertyDescriptorType.STRUCT)
              .collect(Collectors.toMap(CapPropertyDescriptor::getName, rd -> {
                Struct ratioSetting = variantSetting.getStruct(rd.getName());
                Map<String, Object> ratioMap = ratioSetting.toNestedMaps();
                // map default ratio properties
                Ratio ratio = modelMapper.map(ratioMap, Ratio.class);
                // map defined dimensions
                List<Dimension> dimensions = ratioSetting.getType().getDescriptors().stream()
                        .filter(dd -> dd.getType() == CapPropertyDescriptorType.STRUCT)
                        .map(dd -> modelMapper.map(ratioSetting.getStruct(dd.getName()).toNestedMaps(), Dimension.class))
                        .sorted(Comparator.comparingInt(Dimension::getWidth))
                        .collect(Collectors.toList());
                ratio.setDimensions(dimensions);
                return ratio;
              })).entrySet().stream()
              .filter(e -> e.getValue().getWidthRatio() > 0 && e.getValue().getHeightRatio() > 0)
              .sorted(Comparator.comparing(Map.Entry::getKey))
              .collect(Collectors.toMap(Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (v1, v2) -> { throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2)); },
                                        TreeMap::new));
      return new ImageVariantsDescriptor(ratios);
    }
    return new ImageVariantsDescriptor(Collections.emptyMap());
  }
}
