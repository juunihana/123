import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

  public static void main(String[] args) throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(50);
    List<String> someOutput = Collections.synchronizedList(new ArrayList<>());
    AtomicInteger id = new AtomicInteger();

    List<Thread> workers = Stream
        .generate(() -> new Thread(new SomeThread(countDownLatch, someOutput, id.incrementAndGet())))
        .limit(50)
        .collect(Collectors.toList());

    workers.forEach(Thread::start);
    countDownLatch.await();
    someOutput.add("end");

    someOutput.forEach(System.out::println);
  }

  public static class SomeThread implements Runnable {

    CountDownLatch countDownLatch;
    List<String> someOutput;
    int id;

    public SomeThread(CountDownLatch countDownLatch, List<String> someOutput, int id) {
      this.countDownLatch = countDownLatch;
      this.someOutput = someOutput;
      this.id = id;
    }

    @Override
    public void run() {
      someOutput.add("output " + id);
      countDownLatch.countDown();
    }
  }
}
