public abstract class Command {

  private CommandType commandType;
  private EntityType entityType;

  public Command(CommandType commandType,
      EntityType entityType) {
    this.commandType = commandType;
    this.entityType = entityType;
  }

  public CommandType getCommandType() {
    return commandType;
  }

  public void setCommandType(CommandType commandType) {
    this.commandType = commandType;
  }

  public EntityType getEntityType() {
    return entityType;
  }

  public void setEntityType(EntityType entityType) {
    this.entityType = entityType;
  }
}
