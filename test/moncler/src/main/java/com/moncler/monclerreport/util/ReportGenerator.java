package com.moncler.monclerreport.util;

import com.moncler.monclerreport.bean.Report;
import com.moncler.monclerreport.bean.SpreadsheetDocument;
import com.moncler.monclerreport.bean.SpreadsheetItem;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportGenerator {

  private final String TEMPLATE_FILE_NAME = "template.xlsx";
  private final int OFFSET = 15;

  private final SpreadsheetDocument spreadsheetDocument;

  public ReportGenerator(SpreadsheetDocument spreadsheetDocument) {
    this.spreadsheetDocument = spreadsheetDocument;
  }

  public XSSFWorkbook generateReport() throws IOException {
    List<Report> reports = fillReport();

    FileInputStream inputStream = new FileInputStream(TEMPLATE_FILE_NAME);
    XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
    inputStream.close();

    XSSFSheet sheet = workbook.getSheetAt(0);

    Font font = workbook.createFont();
    font.setFontName("Calibri");
    font.setFontHeightInPoints((short) 11);

    CellStyle styleBg = sheet.getRow(15).getCell(new CellReference("L").getCol()).getCellStyle();
    CellStyle styleNoBg = sheet.getRow(15).getCell(new CellReference("C").getCol()).getCellStyle();
    CellStyle styleNoBgFinance = sheet.getRow(15).getCell(new CellReference("K").getCol()).getCellStyle();

    CellStyle styleNoBorders = workbook.createCellStyle();
    styleNoBorders.setBorderTop(BorderStyle.NONE);
    styleNoBorders.setBorderRight(BorderStyle.NONE);
    styleNoBorders.setBorderBottom(BorderStyle.NONE);
    styleNoBorders.setBorderLeft(BorderStyle.NONE);
    styleNoBorders.setFont(font);

    sheet.shiftRows(OFFSET, 26, reports.size(), true, false);

    sheet.getRow(11).getCell(new CellReference("B").getCol())
        .setCellValue("Инвойс: " + spreadsheetDocument.getDate()
            .format(DateTimeFormatter.ofPattern("yyyy")) + " FX " + spreadsheetDocument.getNumber()
            + ", Дата " + spreadsheetDocument.getDate()
            .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            + " Код клиента: С060144\nУсловия оплаты: в течении 90 дней +15 дней");

    int count = OFFSET;
    for (Report report : reports) {
      XSSFRow reportRow = sheet.createRow(count++);
      reportRow.setHeight((short) 900);

      createCell(reportRow, new CellReference("A").getCol(), count - OFFSET, styleNoBg);
      createCell(reportRow, new CellReference("B").getCol(), report.getTamCode(), styleNoBg);
      createCell(reportRow, new CellReference("C").getCol(), report.getArticle(), styleNoBg);
      createCell(reportRow, new CellReference("D").getCol(), report.getName(), styleNoBg);
      createCell(reportRow, new CellReference("E").getCol(), report.getMaterial(), styleNoBg);
      createCell(reportRow, new CellReference("F").getCol(), report.getGender(), styleNoBg);
      createCell(reportRow, new CellReference("G").getCol(), report.getSize(), styleNoBg);
      createCell(reportRow, new CellReference("H").getCol(), report.getOrigin(), styleNoBg);
      createCell(reportRow, new CellReference("I").getCol(), "шт.", styleNoBg);
      createCell(reportRow, new CellReference("J").getCol(), report.getQuantity(), styleNoBg);
      createCell(reportRow, new CellReference("K").getCol(), report.getPriceUnit(), styleNoBgFinance);
      createCell(reportRow, new CellReference("L").getCol(), report.getPriceTotal(), styleBg);
    }

    sheet.removeRow(sheet.getRow(reports.size() + OFFSET));

    String sumFormula = "SUM(L16:L" + (reports.size() + OFFSET) + ")";
    sheet.getRow(reports.size() + OFFSET + 2).getCell(new CellReference("L").getCol())
            .setCellFormula(sumFormula);

    return workbook;
  }


  private List<Report> fillReport() {
    List<Report> reports = new ArrayList<>();

    for (SpreadsheetItem item : spreadsheetDocument.getItems()) {
      Report report = new Report();

      report.setTamCode(item.getTamCode());
      report.setArticle(item.getArticle());
      report.setName(item.getName());
      report.setMaterial(item.getMaterial());
      report.setGender(item.getGender());
      report.setSize(item.getSize());
      report.setOrigin(item.getOrigin());
      report.setQuantity(item.getQuantity());
      report.setPriceUnit(item.getPriceUnit());
      report.setPriceTotal(item.getPriceUnit() * item.getQuantity());

      reports.add(report);
    }

    return reports;
  }

  private void createCell(XSSFRow row, int cellId, String value, CellStyle style) {
    Cell cell = row.createCell(cellId);
    cell.setCellStyle(style);
    cell.setCellValue(value);
  }

  private void createCell(XSSFRow row, int cellId, double value, CellStyle style) {
    Cell cell = row.createCell(cellId);
    cell.setCellStyle(style);
    cell.setCellValue(value);
  }

  private void createCell(XSSFRow row, int cellId, int value, CellStyle style) {
    Cell cell = row.createCell(cellId);
    cell.setCellStyle(style);
    cell.setCellValue(value);
  }
}
