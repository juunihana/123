package com.moncler.monclerreport.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PimCoreGender {

  @JsonProperty("GenderInbound")
  private String genderInbound;

  @JsonProperty("GenderOutbound")
  private String genderOutbound;
}
