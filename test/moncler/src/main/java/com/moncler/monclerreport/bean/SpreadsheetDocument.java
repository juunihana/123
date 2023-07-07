package com.moncler.monclerreport.bean;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpreadsheetDocument {

  private String number;
  private LocalDate date;
  private List<SpreadsheetItem> items;
}
