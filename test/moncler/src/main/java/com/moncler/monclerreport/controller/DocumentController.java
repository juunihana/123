package com.moncler.monclerreport.controller;

import com.moncler.monclerreport.service.DocumentService;
import com.moncler.monclerreport.util.Logger;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentController {

  private final DocumentService documentService;

  @Autowired
  public DocumentController(DocumentService documentService) {
    this.documentService = documentService;
  }

  @GetMapping("/process")
  public void processDocuments() {
    new Thread(() -> {
      try {
        documentService.processDocuments();
      } catch (IOException | GeneralSecurityException e) {
        Logger.error(e.getMessage());
      }
    }).start();
  }
}
