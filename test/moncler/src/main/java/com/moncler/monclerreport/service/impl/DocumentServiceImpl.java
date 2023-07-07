package com.moncler.monclerreport.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.moncler.monclerreport.bean.PimCoreCountry;
import com.moncler.monclerreport.bean.PimCoreDescription;
import com.moncler.monclerreport.bean.PimCoreGender;
import com.moncler.monclerreport.bean.SpreadsheetDocument;
import com.moncler.monclerreport.bean.SpreadsheetItem;
import com.moncler.monclerreport.service.DocumentService;
import com.moncler.monclerreport.service.GoogleDriveManager;
import com.moncler.monclerreport.util.DocumentParser;
import com.moncler.monclerreport.util.Logger;
import com.moncler.monclerreport.util.ReportGenerator;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

@Service
public class DocumentServiceImpl implements DocumentService {

  private final String INBOUND_FOLDER_ID = "1rH12lOSQl-N2AMDb_-fzw8SDyNWwaQiS";
  private final String OUTBOUND_FOLDER_ID = "1ME68M4lCn6obDo6sOzNNHhYeDW5KuH8Y";
  private final String INBOUND_TEMP_FOLDER = "Inbound/";
  private final String OUTBOUND_TEMP_FOLDER = "Outbound/";

  private final GoogleDriveManager googleDriveManager;

  @Autowired
  public DocumentServiceImpl(GoogleDriveManager googleDriveManager) {
    this.googleDriveManager = googleDriveManager;
  }

  @Override
  public void processDocuments() throws IOException, GeneralSecurityException {
    Logger.info("Application start");

    deleteTemporaryFiles();

    downloadFiles();

    Logger.info("Parsing files...");

    java.io.File inboundDirectory = new java.io.File(INBOUND_TEMP_FOLDER);

    String spreadsheetFileName = "";

    for (String fileName : Objects.requireNonNull(inboundDirectory.list())) {
      spreadsheetFileName = INBOUND_TEMP_FOLDER + fileName;
    }

    if (StringUtils.hasLength(spreadsheetFileName)) {
      DocumentParser parser = new DocumentParser(spreadsheetFileName);
      Map<String, SpreadsheetDocument> spreadsheetDocuments = parser.parseSpreadsheets();

      Logger.info("File parsing completed");

      Logger.info("Sending requests to PimCore...");

      List<PimCoreDescription> pimCoreDescriptions = getPimcoreDescription();
      Logger.info("Received descriptions list from PimCore");

      List<PimCoreCountry> pimCoreCountries = getPimcoreCountry();
      Logger.info("Received countries list from PimCore");

      List<PimCoreGender> pimCoreGenders = getPimcoreGender();
      Logger.info("Received genders list from PimCore");

      Logger.info("Matching data...");

      for (SpreadsheetDocument document : spreadsheetDocuments.values()) {
        Logger.info("Matching document " + document.getNumber() + "...");
        for (SpreadsheetItem item : document.getItems()) {
          for (PimCoreDescription description : pimCoreDescriptions) {
            if (item.getName().toLowerCase().contains(description.getDescriptionInbound().toLowerCase())) {
              item.setName(description.getDescriptionOutbound());
              break;
            }
          }

          for (PimCoreCountry country : pimCoreCountries) {
            if (item.getOrigin().equalsIgnoreCase(country.getCountryInbound())) {
              item.setOrigin(country.getCountryOutbound());
              break;
            }
          }

          for (PimCoreGender gender : pimCoreGenders) {
            if (item.getGender().equalsIgnoreCase(gender.getGenderInbound())) {
              item.setGender(gender.getGenderOutbound());
              break;
            }
          }
        }

        List<SpreadsheetItem> itemsTmp = new ArrayList<>();
        for (SpreadsheetItem item : document.getItems()) {
          int existingIndex = 0;
          boolean found = false;
          for (SpreadsheetItem existingItem : itemsTmp) {
            if (item.getArticle().equalsIgnoreCase(existingItem.getArticle())
                && item.getTamCode().equalsIgnoreCase(existingItem.getTamCode())
                && item.getName().equalsIgnoreCase(existingItem.getName())
                && item.getMaterial().equalsIgnoreCase(existingItem.getMaterial())
                && item.getGender().equalsIgnoreCase(existingItem.getGender())
                && item.getOrigin().equalsIgnoreCase(existingItem.getOrigin())
                && item.getPriceUnit() == existingItem.getPriceUnit()) {
              found = true;
              break;
            } else {
              existingIndex++;
            }
          }
          if (found) {
            itemsTmp.get(existingIndex)
                .setQuantity(itemsTmp.get(existingIndex).getQuantity() + item.getQuantity());
            itemsTmp.get(existingIndex)
                .setSize(itemsTmp.get(existingIndex).getSize() + ", " + item.getSize());
          } else {
            itemsTmp.add(item);
          }
        }
        document.setItems(itemsTmp);
      }

      Logger.info("Documents matched");

      Logger.info("Generating report...");

      for (SpreadsheetDocument document : spreadsheetDocuments.values()) {
        ReportGenerator generator = new ReportGenerator(document);
        XSSFWorkbook report = generator.generateReport();
        saveReport(report, document.getDate().format(
            DateTimeFormatter.ofPattern("yyyy")) + " fx " + document.getNumber() + ".xlsx");
      }

      Logger.info("Uploading report...");

      uploadReports();
      deleteTemporaryFiles();

      Logger.info("Work completed");
    } else {
      Logger.info("Inbound directory does not contain required files");
    }
  }

  private List<PimCoreDescription> getPimcoreDescription() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(mapper.writeValueAsString(mapper.readTree(
            getPimCoreData(
                "http://fmlpimcore.kt-team.de/pimcore-graphql-webservices/MonclerDescription?apikey=1b9ae10dddce3f8c410163ab43b69880",
                "{ getObjectFolder(id: 190757) { children { ... on object_MonclerDescription { DescriptionInbound DescriptionOutbound } } } }"
            )).get("data").get("getObjectFolder").get("children")),
        new TypeReference<List<PimCoreDescription>>() {
        });
  }

  private List<PimCoreCountry> getPimcoreCountry() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(mapper.writeValueAsString(mapper.readTree(
            getPimCoreData(
                "http://fmlpimcore.kt-team.de/pimcore-graphql-webservices/MonclerCountry?apikey=709612c879fbbd2c43a756f9131538f2",
                "{ getObjectFolder(id: 190694) { children { ... on object_MonclerCountry { CountryInbound CountryOutbound } } } }"
            )).get("data").get("getObjectFolder").get("children")),
        new TypeReference<List<PimCoreCountry>>() {
        });
  }

  private List<PimCoreGender> getPimcoreGender() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(mapper.writeValueAsString(mapper.readTree(
            getPimCoreData(
                "http://fmlpimcore.kt-team.de/pimcore-graphql-webservices/MonclerGender?apikey=73375fcd91181d1b08496258a43c20e1",
                "{ getObjectFolder(id: 190744) { children { ... on object_MonclerGender { GenderInbound GenderOutbound } } } }"
            )).get("data").get("getObjectFolder").get("children")),
        new TypeReference<List<PimCoreGender>>() {
        });
  }


  private String getPimCoreData(String url, String query) throws IOException {
    StringBuilder result = new StringBuilder();

    try (CloseableHttpClient client = HttpClients.createDefault()) {
      String inputJson = "{ \"query\": \"" + query + "\" }";
      StringEntity requestEntity = new StringEntity(inputJson);

      HttpPost request = new HttpPost(url);
      request.setHeader("Content-Type", "application/json");
      request.setEntity(requestEntity);

      HttpResponse response = client.execute(request);
      BufferedReader br = new BufferedReader(
          new InputStreamReader((response.getEntity().getContent()))
      );

      if (response.getStatusLine().getStatusCode() != 200) {
        throw new RuntimeException("Failed : HTTP error code : " +
            response.getStatusLine().getStatusCode());
      }

      String line;
      while ((line = br.readLine()) != null) {
        result.append(line);
      }
    }

    return result.toString();
  }


  private void downloadFiles() throws IOException, GeneralSecurityException {
    Logger.info("Downloading files...");

    Logger.info("Getting file list...");

    FileList result = googleDriveManager.getInstance().files().list()
        .setQ("'" + INBOUND_FOLDER_ID + "' in parents and trashed = false")
        .setSupportsAllDrives(true)
        .setIncludeItemsFromAllDrives(true)
        .setCorpora("drive")
        .setDriveId("0ALUBA3jpmbUWUk9PVA")
        .setFields("nextPageToken, files(id, name)")
        .execute();

    for (File testFile : result.getFiles()) {
      Logger.info("Downloading file " + testFile.getName() + "...");

      File file = googleDriveManager.getInstance().files()
          .get(testFile.getId())
          .setSupportsAllDrives(true)
          .execute();
      OutputStream outputStream = new FileOutputStream(INBOUND_TEMP_FOLDER + file.getName());
      googleDriveManager.getInstance().files()
          .get(file.getId())
          .setSupportsAllDrives(true)
          .executeMediaAndDownloadTo(outputStream);
      outputStream.close();

      Logger.info("File " + file.getName() + " downloaded");
    }
  }

  private void saveReport(XSSFWorkbook workbook, String reportFileName) throws IOException {
    OutputStream outputStream = new FileOutputStream(OUTBOUND_TEMP_FOLDER + reportFileName);
    workbook.write(outputStream);
    outputStream.close();
    workbook.close();

    Logger.info("Report " + reportFileName + " saved");
  }

  private void uploadReports() throws IOException, GeneralSecurityException {
    java.io.File outboundFolder = new java.io.File(OUTBOUND_TEMP_FOLDER);
    for (String fileName : Objects.requireNonNull(outboundFolder.list())) {
      InputStream inputStream = new FileInputStream(OUTBOUND_TEMP_FOLDER + fileName);
      File fileMetadata = new File();
      fileMetadata.setParents(Collections.singletonList(OUTBOUND_FOLDER_ID));
      fileMetadata.setName(fileName);

      googleDriveManager.getInstance()
          .files()
          .create(fileMetadata, new InputStreamContent(
              "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
              inputStream))
          .setFields("id")
          .setSupportsAllDrives(true)
          .execute();

      Logger.info("Report " + fileName + " uploaded");
    }
  }

  private void deleteTemporaryFiles() throws IOException {
    java.io.File inboundTempFolder = new java.io.File(INBOUND_TEMP_FOLDER);
    java.io.File outboundTempFolder = new java.io.File(OUTBOUND_TEMP_FOLDER);

    for (String fileName : Objects.requireNonNull(inboundTempFolder.list())) {
      Files.delete(Paths.get(INBOUND_TEMP_FOLDER + fileName));
    }

    for (String fileName : Objects.requireNonNull(outboundTempFolder.list())) {
      Files.delete(Paths.get(OUTBOUND_TEMP_FOLDER + fileName));
    }
  }
}
