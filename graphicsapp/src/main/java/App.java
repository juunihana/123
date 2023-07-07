import java.awt.Color;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class App {

  public static void main(String[] args) {
    JFrame frame = new JFrame("Graphics");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(1200, 1200);

    Canvas canvas = new Canvas();
    canvas.setSize(1000, 1000);
    canvas.setBackground(Color.WHITE);

    Timer timer = new Timer(15, e -> {
        canvas.repaint();
    });
    timer.start();

    frame.add(canvas);
    frame.setVisible(true);
  }
}
