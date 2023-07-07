import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Document {

  private String name;
  private Map<String, String> data;
  private LocalDateTime creationTime;

  public Document(String name) {
    this.name = name;
    this.data = new HashMap<>();
    this.creationTime = LocalDateTime.now();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, String> getData() {
    return data;
  }

  public void setData(Map<String, String> data) {
    this.data = data;
  }

  public LocalDateTime getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(LocalDateTime creationTime) {
    this.creationTime = creationTime;
  }
}
