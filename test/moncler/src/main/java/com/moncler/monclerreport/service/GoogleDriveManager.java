package com.moncler.monclerreport.service;

import com.google.api.services.drive.Drive;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleDriveManager {

  Drive getInstance() throws GeneralSecurityException, IOException;
}
