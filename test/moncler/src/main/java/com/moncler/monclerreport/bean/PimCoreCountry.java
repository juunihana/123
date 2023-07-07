package com.moncler.monclerreport.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PimCoreCountry {

  @JsonProperty("CountryInbound")
  private String countryInbound;

  @JsonProperty("CountryOutbound")
  private String countryOutbound;
}
