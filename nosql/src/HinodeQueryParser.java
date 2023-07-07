import java.util.List;
import java.util.Set;

public class HinodeQueryParser {

  private final Set<String> TOKENS = Set.of("make", "store", "find", "remove", "in");

  private static HinodeQueryParser instance = null;

  private HinodeQueryParser() {
  }

  public static HinodeQueryParser getInstance() {
    if (instance == null) {
      instance = new HinodeQueryParser();
    }

    return instance;
  }

  public void parse(String commandLine) {
    String initialToken = commandLine.trim().split(" ")[0];
    if (!TOKENS.contains(initialToken)) {
      throw new RuntimeException("unknown command");
    }

    GenericCommand command = new GenericCommand();

    switch (initialToken) {
      case "make" -> {
//        String documentToken = commandLine.trim().split(" ")[1];
        String documentName = commandLine.trim().split(" ")[2];

        command.setType(initialToken);
        command.setDocument(documentName);
      }
      case "store" -> {
        
      }
      case "find" -> {

      }
      case "remove" -> {

      }
    }
  }

  public List<String> getTokens(String commandLine) {
    return List.of(commandLine.split(" "));
  }

  public CommandType resolveCommandType(String token) {
    List<String> allowedFirstTokens = List.of("make", "store", "find", "remove");

    if (!allowedFirstTokens.contains(token)) {
      throw new RuntimeException("syntax error");
    }

    return switch (token.trim().toLowerCase()) {
      case "make" -> CommandType.MAKE;
      case "store" -> CommandType.STORE;
      case "find" -> CommandType.FIND;
      case "remove" -> CommandType.REMOVE;
      default -> throw new RuntimeException("cannot resolve command type");
    };
  }

  public EntityType resolveEntityType(String token) {
//    List<String> allowedSecondTokens = List.of("doc", "value", "");
//
//    if (!allowedSecondTokens.contains(token)) {
//      throw new RuntimeException("syntax error");
//    }

    return switch (token.trim().toLowerCase()) {
      case "doc" -> EntityType.DOC;
      case "value" -> EntityType.VALUE;
      default -> EntityType.VALUE;//throw new RuntimeException("cannot resolve entity type");
    };
  }
}
