package com.moncler.monclerreport.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpreadsheetItem {

  private String tamCode;
  private String article;
  private String name;
  private String material;
  private String gender;
  private String size;
  private String origin;
  private int quantity;
  private double priceUnit;
}
