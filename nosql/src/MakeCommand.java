public class MakeCommand extends Command {

  private String name;

  public MakeCommand(String name) {
    super(CommandType.MAKE, EntityType.DOC);
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
