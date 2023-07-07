import java.util.ArrayList;
import java.util.List;

public class App {

  public static void main(String[] args) throws InterruptedException {
    long t1 = System.currentTimeMillis();

    List<Integer> integers = new ArrayList<>();
    for (int i = 0; i < 999; i++) {
      integers.add(i);
      Thread.sleep(1);
    }

    long t2 = System.currentTimeMillis();

    System.out.println(t2 - t1);

    List<Integer> strings = integers.stream()
        .map(number -> 1 + number).toList();

    long t3 = System.currentTimeMillis();

    System.out.println(t3 - t2);

    System.out.println(t3 - t1);
  }
}