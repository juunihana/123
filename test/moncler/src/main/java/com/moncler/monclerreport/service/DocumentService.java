package com.moncler.monclerreport.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface DocumentService {

  void processDocuments() throws IOException, GeneralSecurityException;
}
