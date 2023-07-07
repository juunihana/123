package com.moncler.monclerreport.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PimCoreDescription {

  @JsonProperty("DescriptionInbound")
  private String descriptionInbound;

  @JsonProperty("DescriptionOutbound")
  private String descriptionOutbound;
}
