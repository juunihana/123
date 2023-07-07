import java.util.List;

public class FindCommand extends Command {

  private String name;
  private List<String> fields;

  public FindCommand(String name, String fields) {
    super(CommandType.FIND, EntityType.DOC);
    this.name = name;
    if (!fields.equalsIgnoreCase("__all__")) {
      this.fields = List.of(fields.split(":"));
    } else {
      this.fields = List.of("__all__");
    }
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
