import java.util.List;

public class RemoveFieldCommand extends Command {

  private String name;
  private List<String> fields;

  public RemoveFieldCommand(EntityType type, String name, String fields) {
    super(CommandType.REMOVE, type);
    this.name = name;
    this.fields = List.of(fields.split(":"));
  }

  public List<String> getFields() {
    return fields;
  }

  public void setFields(List<String> fields) {
    this.fields = fields;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
