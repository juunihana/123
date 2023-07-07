import java.util.concurrent.CountDownLatch;

public class Cart implements Runnable {

  private final String cartName;
  private CountDownLatch startLatch;
  private CountDownLatch finishLatch;

  public Cart(String cartName,
      CountDownLatch startLatch,
      CountDownLatch finishLatch) {
    this.cartName = cartName;
    this.startLatch = startLatch;
    this.finishLatch = finishLatch;
  }

  public void run() {
    try {
      moveToStart();
      startLatch.await();
      start();
      finishLatch.await();
      finish();
    } catch (InterruptedException e) {
      System.out.println(e.getMessage());
    }
  }

  private void moveToStart() {

  }

  private void start() {

  }

  private void finish() {

  }

}
