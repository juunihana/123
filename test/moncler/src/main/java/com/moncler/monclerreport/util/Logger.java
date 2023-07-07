package com.moncler.monclerreport.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

  public static void info(short value) {
    log(String.valueOf(value), LoggerTypes.INFO);
  }

  public static void info(int value) {
    log(String.valueOf(value), LoggerTypes.INFO);
  }

  public static void info(long value) {
    log(String.valueOf(value), LoggerTypes.INFO);
  }

  public static void info(float value) {
    log(String.valueOf(value), LoggerTypes.INFO);
  }

  public static void info(double value) {
    log(String.valueOf(value), LoggerTypes.INFO);
  }

  public static void info(String message) {
    log(message, LoggerTypes.INFO);
  }

  public static void error(String message) {
    log(message, LoggerTypes.ERROR);
  }

  private static void log(String message, LoggerTypes type) {
    String typeString = "";

    switch (type) {
      case INFO:
        typeString = "INFO";
        break;

      case ERROR:
        typeString = "ERROR";
        break;
    }

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    StringBuilder logSb = new StringBuilder();
    logSb.append("[");
    logSb.append(LocalDateTime.now().format(dtf));
    logSb.append("]");
    logSb.append("[");
    logSb.append(typeString);
    logSb.append("] ");
    logSb.append(message);

    System.out.println(logSb);
  }
}
