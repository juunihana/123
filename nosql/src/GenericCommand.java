import java.util.Map;

public class GenericCommand {

  private String type;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDocument() {
    return document;
  }

  public void setDocument(String document) {
    this.document = document;
  }

  public Map<String, String> getFields() {
    return fields;
  }

  public void setFields(Map<String, String> fields) {
    this.fields = fields;
  }

  private String document;

  public GenericCommand() {}

  public GenericCommand(String type, String document,
      Map<String, String> fields) {
    this.type = type;
    this.document = document;
    this.fields = fields;
  }

  private Map<String, String> fields; //keys and values
}
