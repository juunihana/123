package com.moncler.monclerreport.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.moncler.monclerreport.service.GoogleDriveManager;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GoogleDriveManagerImpl implements GoogleDriveManager {

  private static final String APPLICATION_NAME = "Moncler Report";
  private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
  private static final String CREDENTIALS_FILE_PATH = "key.p12";
//    private static final String CREDENTIALS_FILE_PATH = "/home/spring/key.p12";

  @Override
  public Drive getInstance() throws GeneralSecurityException, IOException {
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    java.io.File pk12 = new java.io.File(CREDENTIALS_FILE_PATH);
    String serviceAccount = "ru-vision-api-sa-drive@ru-build-vision-api.iam.gserviceaccount.com";

    @SuppressWarnings("deprecation")
    GoogleCredential getCredentials = new GoogleCredential.Builder()
        .setTransport(HTTP_TRANSPORT)
        .setJsonFactory(JSON_FACTORY)
        .setServiceAccountId(serviceAccount)
        .setServiceAccountPrivateKeyFromP12File(pk12)
        .setServiceAccountScopes(SCOPES)
        .build();

    return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials)
        .setApplicationName(APPLICATION_NAME)
        .build();
  }
}
