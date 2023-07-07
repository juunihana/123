import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;

public class InventorySystemApp {

  static List<Item> inventory;

  public static void main(String[] args) {
    inventory = new ArrayList<>();
    inventory.add(new Item("Sword"));
    inventory.add(new Item("Wood"));
    inventory.add(new Item("Glass"));
    inventory.add(new Item("Shirt"));
    inventory.add(new Item("Book"));
    inventory.add(new Item("Wood"));
    inventory.add(new Item("Wood"));

    JFrame frame = new JFrame("Inventory System");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setLayout(null);

    int count = 0, offsetX = 0, offsetY = 0;
    for (Item item : inventory) {
      JButton button = new JButton(item.getName());
      button.setLocation(10 + offsetX * 100, 10 + offsetY * 30);
      button.setSize(100, 30);
      frame.getContentPane().add(button);
      count++;
      if (offsetX == 4) { offsetX = 0; offsetY++; } else offsetX++;
    }

    frame.setVisible(true);
  }

  public static class Item {

    private String name;

    public Item(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
