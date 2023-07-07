import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class App {

  private static final Set<String> HELP_COMMANDS = Set.of("help", "h");
  private static final Set<String> LIST_COMMANDS = Set.of("list", "l");
  private static final Set<String> MERCHANT_COMMANDS = Set.of("merchant", "m");
  private static final Set<String> BUY_COMMANDS = Set.of("buy", "b");
  private static final Set<String> SELL_COMMANDS = Set.of("sell", "s");
  private static final Set<String> QUIT_COMMANDS = Set.of("quit", "q");

  private static List<Item> playerItems;
  private static List<Item> merchantItems;
  private static Inventory playerInventory;
  private static Inventory merchantInventory;

  public static void main(String[] args) {
    LocalDateTime tmp = LocalDateTime.parse("11:00", DateTimeFormatter.ofPattern("hh:mm"));

    init();

    System.out.println("Inventory system v0.1");
    Scanner in = new Scanner(System.in);

    String command = "";
    while (!(QUIT_COMMANDS.contains(command))) {
      System.out.print("> ");
      command = in.nextLine();

      if (HELP_COMMANDS.contains(command.split(" ")[0])) {
        printHelp();
      }

      if (LIST_COMMANDS.contains(command.split(" ")[0])) {
        printInventory(playerInventory);
      }

      if (MERCHANT_COMMANDS.contains(command.split(" ")[0])) {
        printInventory(merchantInventory);
      }

      if (BUY_COMMANDS.contains(command.split(" ")[0])) {
        itemTransaction(merchantInventory, playerInventory, Integer.parseInt(command.split(" ")[1]));
      }

      if (SELL_COMMANDS.contains(command.split(" ")[0])) {
        itemTransaction(playerInventory, merchantInventory, Integer.parseInt(command.split(" ")[1]));
      }
    }

    in.close();
  }

  private static void init() {
    playerItems = new ArrayList<>();
    playerItems.add(new Item(1, 10, "Book"));
    playerItems.add(new Item(1, 2, "Paper"));
    playerItems.add(new Item(1, 50, "Leather coat"));

    merchantItems = new ArrayList<>();
    merchantItems.add(new Item(1, 5, "Pencil"));
    merchantItems.add(new Item(1, 7, "Candle"));
    merchantItems.add(new Item(1, 120, "Silver ring"));

    playerInventory = new Inventory(100, playerItems);
    merchantInventory = new Inventory(100, merchantItems);
  }

  private static void printHelp() {
    System.out.println("help h - show help");
    System.out.println("list l - list player items");
    System.out.println("merchant m - list merchant items");
    System.out.println("buy b <number> - buy item");
    System.out.println("sell s <number> - sell item");
    System.out.println("quit q - quit inventory system");
  }

  private static void printInventory(Inventory inventory) {
    System.out.println("Total money: " + inventory.getMoney());
    int i = 0;
    for (Item item : inventory.getItems()) {
      System.out.println("[" + i + "] " + item.getName() + "; Price: " + item.getPrice());
      i++;
    }
  }

  private static void itemTransaction(Inventory src, Inventory dst, int id) {
    if (!dst.withdrawMoney(src.getItems().get(id).getPrice())) {
      System.out.println("You have insufficient funds");
    } else {
      src.addMoney(src.getItems().get(id).getPrice());
      dst.addItem(src.getItems().get(id));
      src.removeItem(id);
    }
  }
}
