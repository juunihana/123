import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HinodeDB {

  private Map<String, Document> database;
  private final Logger log = Logger.getInstance();

  public HinodeDB() {
    this.database = new HashMap<>();
  }

  public String dispatch(String command) {
    return executeCommand(parseCommand(command));
  }

  private String executeCommand(Command command) {
    switch (command.getCommandType()) {
      case MAKE -> {
        switch (command.getEntityType()) {
          case DOC -> createDoc((MakeCommand) command);
        }
      }
      case STORE -> {
        switch (command.getEntityType()) {
          case VALUE -> storeValue((StoreCommand) command);
        }
      }
      case FIND -> {
        return switch (command.getEntityType()) {
          case DOC -> findFields((FindCommand) command);
          default -> throw new RuntimeException("unknown entity");
        };
      }
      case REMOVE -> {
        switch (command.getEntityType()) {
          case DOC -> removeDoc((RemoveDocCommand) command);
          case VALUE -> removeField((RemoveFieldCommand) command);
        }
      }
    }

    return "";
  }

  private void createDoc(MakeCommand makeCommand) {
    database.put(makeCommand.getName(), new Document(makeCommand.getName()));
  }

  private void storeValue(StoreCommand storeCommand) {
    if (database.get(storeCommand.getName()) == null) {
      throw new RuntimeException("doc does not exist");
    }
    database.get(storeCommand.getName()).getData()
        .put(storeCommand.getKey(), storeCommand.getValue());
  }

  private String findFields(FindCommand findCommand) {
    if (database.get(findCommand.getName()) == null) {
      log.info("Document " + findCommand.getName() + " was not found");
      return "";
    }

    Map<String, String> result = new HashMap<>();

    if (findCommand.getFields().get(0).equals("__all__")) {
      for (String field : database.get(findCommand.getName()).getData().keySet()) {
        result.put(field, database.get(findCommand.getName()).getData().get(field));
      }
    } else {
      for (String field : findCommand.getFields()) {
        String valueFromDb = database.get(findCommand.getName()).getData().get(field);
        if (database.get(findCommand.getName()).getData().get(field) == null) {
          log.info(
              "Field " + findCommand.getName() + " in document " + findCommand.getName()
                  + " was not found");
          continue;
        }
        result.put(field, valueFromDb);
      }
    }

    StringBuilder sb = new StringBuilder();
    for (String field : result.keySet()) {
      sb.append(field).append(":").append(result.get(field)).append(",");
    }

    sb.deleteCharAt(sb.length() - 1);

    return sb.toString();
  }

  private void removeDoc(RemoveDocCommand removeCommand) {
    if (database.get(removeCommand.getName()) == null) {
      throw new RuntimeException("doc does not exist");
    }
    database.remove(removeCommand.getName());
  }

  private void removeField(RemoveFieldCommand findCommand) {
    if (database.get(findCommand.getName()) == null) {
      log.info("Document " + findCommand.getName() + " was not found");
    }

    for (String field : findCommand.getFields()) {
      if (database.get(findCommand.getName()).getData().get(field) == null) {
        log.info(
            "Field " + findCommand.getName() + " in document " + findCommand.getName()
                + " was not found");
        continue;
      }

      database.get(findCommand.getName()).getData().remove(field);
    }
  }

  private Command parseCommand(String commandLine) {
    log.info(commandLine);
    HinodeQueryParser parser = HinodeQueryParser.getInstance();

    List<String> tokens = parser.getTokens(commandLine);

    return switch (parser.resolveCommandType(tokens.get(0))) {
      case MAKE -> switch (parser.resolveEntityType(tokens.get(1))) {
        case DOC -> new MakeCommand(tokens.get(2));
        default -> null;
      };

      case STORE -> new StoreCommand(tokens.get(3), tokens.get(1).split(":")[0],
          tokens.get(1).split(":")[1]);

      case FIND -> new FindCommand(tokens.get(3), tokens.get(1));

      case REMOVE -> switch (parser.resolveEntityType(tokens.get(1))) {
        case DOC -> new RemoveDocCommand(EntityType.DOC, tokens.get(2));
        default -> new RemoveFieldCommand(EntityType.VALUE, tokens.get(3), tokens.get(1));
      };
    };
  }
}
