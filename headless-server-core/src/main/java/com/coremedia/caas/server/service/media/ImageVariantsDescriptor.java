package com.coremedia.caas.server.service.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Ordering;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ImageVariantsDescriptor {

  private Map<String, Ratio> ratios;


  ImageVariantsDescriptor(Map<String, Ratio> ratios) {
    setRatios(ratios);
  }


  @ApiModelProperty(example = "{" +
                              "\"landscape_ratio16x9\": {" +
                              "  \"minWidth\": 320," +
                              "  \"minHeight\": 180," +
                              "  \"widthRatio\": 16," +
                              "  \"heightRatio\": 9," +
                              "  \"dimensions\": [" +
                              "    {\"width\": 320,\"height\": 180}," +
                              "    {\"width\": 640,\"height\": 360}," +
                              "    {\"width\": 1280,\"height\": 720}" +
                              "  ]" +
                              "}," +
                              "\"landscape_ratio4x3\": {" +
                              "  \"minWidth\": 320," +
                              "  \"minHeight\": 240," +
                              "  \"widthRatio\": 4," +
                              "  \"heightRatio\": 3," +
                              "  \"dimensions\": [" +
                              "    {\"width\": 320,\"height\": 240}," +
                              "    {\"width\": 640,\"height\": 480}," +
                              "    {\"width\": 1280,\"height\": 960}" +
                              "  ]" +
                              "}" +
                              "}")
  @JsonProperty
  public Map<String, Ratio> getRatios() {
    return ratios;
  }

  public void setRatios(Map<String, Ratio> ratios) {
    this.ratios = ratios;
  }


  public static class Ratio {

    private int minWidth;
    private int minHeight;
    private int widthRatio;
    private int heightRatio;

    private List<Dimension> dimensions = new ArrayList<>();

    @JsonProperty
    public int getMinWidth() {
      return minWidth;
    }

    public void setMinWidth(int minWidth) {
      this.minWidth = minWidth;
    }

    @JsonProperty
    public int getMinHeight() {
      return minHeight;
    }

    public void setMinHeight(int minHeight) {
      this.minHeight = minHeight;
    }

    @JsonProperty
    public int getWidthRatio() {
      return widthRatio;
    }

    public void setWidthRatio(int widthRatio) {
      this.widthRatio = widthRatio;
    }

    @JsonProperty
    public int getHeightRatio() {
      return heightRatio;
    }

    public void setHeightRatio(int heightRatio) {
      this.heightRatio = heightRatio;
    }

    @JsonProperty
    public List<Dimension> getDimensions() {
      return dimensions;
    }

    public void setDimensions(List<Dimension> dimensions) {
      this.dimensions = Ordering.from(Comparator.comparingInt(Dimension::getWidth)).sortedCopy(dimensions);
    }
  }


  public static class Dimension {

    private int width;
    private int height;

    public Dimension(int width, int height) {
      this.width = width;
      this.height = height;
    }

    @JsonProperty
    public int getWidth() {
      return width;
    }

    public void setWidth(int width) {
      this.width = width;
    }

    @JsonProperty
    public int getHeight() {
      return height;
    }

    public void setHeight(int height) {
      this.height = height;
    }
  }
}
