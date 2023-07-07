package com.example.restchat;

import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1")
public class RestchatApplication {

  public static void main(String[] args) {
    SpringApplication.run(RestchatApplication.class, args);
  }

  @CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
  @GetMapping("/endpoint")
  public ResponseEntity<?> getSomething(
      @RequestParam String username
  ) {
    return ResponseEntity.ok().body(Map.of("greeting", "hello, " + username));
  }

}
