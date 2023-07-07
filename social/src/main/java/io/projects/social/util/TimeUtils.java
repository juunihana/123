package io.projects.social.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

  public static String dateTimeToString(LocalDateTime dateTime) {
    return dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss MMMM dd, yyyy"));
  }
}
