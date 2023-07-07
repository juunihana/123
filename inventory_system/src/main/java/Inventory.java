import java.util.List;

public class Inventory {
  private int money;
  private List<Item> items;

  public Inventory(int money, List<Item> items) {
    this.money = money;
    this.items = items;
  }

  public int getMoney() {
    return money;
  }

  public void setMoney(int money) {
    this.money = money;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public void addItem(Item item) {
    this.items.add(item);
  }

  public void removeItem(int id) {
    this.items.remove(id);
  }

  public void addMoney(int value) {
    this.money += value;
  }

  public boolean withdrawMoney(int value) {
    if (this.money - value < 0) {
      return false;
    }
    this.money -= value;
    return true;
  }
}
