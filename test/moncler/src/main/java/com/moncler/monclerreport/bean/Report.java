package com.moncler.monclerreport.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

  private String tamCode;
  private String article;
  private String name;
  private String material;
  private String gender;
  private String size;
  private String origin;
  private String unit;
  private int quantity;
  private double priceUnit;
  private double priceTotal;
}
