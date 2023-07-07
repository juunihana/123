public class StoreCommand extends Command {

  private String name;
  private String key;
  private String value;

  public StoreCommand(String name,
      String key,
      String value) {
    super(CommandType.STORE, EntityType.VALUE);
    this.name = name;
    this.key = key;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }
}
