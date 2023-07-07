import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

  private static Logger instance;

  private Logger() {

  }

  public static Logger getInstance() {
    if (instance == null) {
      instance = new Logger();
    }

    return instance;
  }

  public void info(String message) {
    if (!message.equals("")) {
      System.out.printf("[%s] %s%n",
          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), message);
    }
  }
}
