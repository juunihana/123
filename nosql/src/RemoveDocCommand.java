public class RemoveDocCommand extends Command {
  private String name;

  public RemoveDocCommand(EntityType type, String name) {
    super(CommandType.REMOVE, type);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
