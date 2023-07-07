package com.moncler.monclerreport.util;

import com.moncler.monclerreport.bean.SpreadsheetDocument;
import com.moncler.monclerreport.bean.SpreadsheetItem;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

public class DocumentParser {

  private final String spreadsheetFileName;

  private final Map<String, Integer> columns = new HashMap<>();

  public DocumentParser(String spreadsheetFileName) {
    this.spreadsheetFileName = spreadsheetFileName;
  }

  public Map<String, SpreadsheetDocument> parseSpreadsheets() throws IOException {
    Map<String, SpreadsheetDocument> spreadsheetDocument = new HashMap<>();

    Workbook workbook;

    try {
      FileInputStream inputStream = new FileInputStream(spreadsheetFileName);
      workbook = new HSSFWorkbook(inputStream);
    } catch (Exception e) {
      FileInputStream inputStream = new FileInputStream(spreadsheetFileName);
      workbook = new XSSFWorkbook(inputStream);
    }

    Sheet sheet = workbook.getSheetAt(0);

    Iterator<Row> iterator = sheet.rowIterator();

    boolean tableBegin = false;
    while (iterator.hasNext()) {
      Row row = iterator.next();
      try {
        if (!tableBegin) {
          if (StringUtils.hasLength(readCellData("A", row))) {
            readColumns(row);
            tableBegin = true;
          }
          continue;
        }

        String invNum = readCellData(
            new CellReference(row.getRowNum(), columns.get("INV_INVOICE")).formatAsString(), row);

        if (!StringUtils.hasLength(invNum)) {
          break;
        }

        invNum = String.valueOf((int) Double.parseDouble(invNum));

        if (spreadsheetDocument.containsKey(invNum)) {
          spreadsheetDocument.get(invNum).getItems().add(readItem(row));
        } else {
          spreadsheetDocument.putIfAbsent(invNum, new SpreadsheetDocument());

          spreadsheetDocument.get(invNum).setNumber(invNum);

          spreadsheetDocument.get(invNum).setDate(readDateCellData(
              new CellReference(row.getRowNum(), columns.get("INV_DATE")).formatAsString(),
              row).toLocalDate());

          spreadsheetDocument.get(invNum).setItems(new ArrayList<>());
          spreadsheetDocument.get(invNum).getItems().add(readItem(row));
        }
      } catch (Exception e) {
        e.printStackTrace();
        Logger.error("Row number " + row.getRowNum() + " in file  " + spreadsheetFileName);
      }
    }

    return spreadsheetDocument;
  }

  private SpreadsheetItem readItem(Row row) {
    return SpreadsheetItem.builder()
        .tamCode(readCell("EU", row))
        .article(readCell("ITEM", row))
        .name(readCell("DESCRIPTION", row))
        .material(readCell("COMPOSITION", row))
        .gender(readCell("GENDER", row))
        .size(readCell("SIZE", row))
        .origin(readCell("MADE IN DESCR", row))
        .quantity((int) Double.parseDouble(readCell("QTY", row)))
        .priceUnit(Double.parseDouble(readCell("UNIT PRICE", row)))
        .build();
  }

  private void readColumns(Row row) {
    int initColumn = 0;
    String currentColumn = "INIT";
    while (StringUtils.hasLength(currentColumn)) {
      currentColumn = readCellData(
          new CellReference(row.getRowNum(), initColumn).formatAsString(), row);
      columns.putIfAbsent(currentColumn.trim(), initColumn);
      initColumn++;
    }
  }

  private String readCell(String column, Row row) {
    return readCellData(
        new CellReference(row.getRowNum(), columns.get(column)).formatAsString(), row);
  }

  private String readCellData(String cellReference, Row row) {
    try {

      switch (row.getCell(new CellReference(cellReference).getCol()).getCellType()) {
        case FORMULA:
          switch (row.getCell(new CellReference(cellReference).getCol())
              .getCachedFormulaResultType()) {
            case STRING:
              return readStringCellData(cellReference, row);
            case NUMERIC:
              if (DateUtil.isCellDateFormatted(
                  row.getCell(new CellReference(cellReference).getCol()))) {
                return readDateCellData(cellReference, row).format(
                    DateTimeFormatter.ofPattern("dd.MM.yyyy"));
              } else {
                return String.valueOf(readNumericCellData(cellReference, row));
              }
          }
        case NUMERIC:
          if (DateUtil.isCellDateFormatted(
              row.getCell(new CellReference(cellReference).getCol()))) {
            return readDateCellData(cellReference, row).format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy"));
          } else {
            return String.valueOf(readNumericCellData(cellReference, row));
          }
        default:
          return readStringCellData(cellReference, row);
      }
    } catch (Exception e) {
      return "";
    }
  }

  private String readStringCellData(String cellReference, Row row) {
    return row.getCell(new CellReference(cellReference).getCol()).getStringCellValue();
  }

  private double readNumericCellData(String cellReference, Row row) {
    return row.getCell(new CellReference(cellReference).getCol()).getNumericCellValue();
  }

  private LocalDateTime readDateCellData(String cellReference, Row row) {
    return row.getCell(new CellReference(cellReference).getCol()).getLocalDateTimeCellValue();
  }
}
