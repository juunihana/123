import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Canvas extends JPanel {

  private final int OFFSET = 100;
  int x1 = 200, y1 = 200, x2 = 400, y2 = 200, x3 = 200, y3 = 400, x4 = 400, y4 = 400;
  double angle = 0;

  public Canvas() {
  }

  public void paintComponent(Graphics g) {
    if (angle >= 6) {
      angle = 0;
    } else {
      angle += 0.1;
    }

    x1 = 200 + (int) (Math.cos(angle));
    x2 = 400 + (int) (Math.cos(angle));
    x3 = 200 + (int) (Math.cos(angle));
    x4 = 400 + (int) (Math.cos(angle));

    y1 = 200 + (int) (Math.sin(angle));
    y2 = 200 + (int) (Math.sin(angle));
    y3 = 400 + (int) (Math.sin(angle));
    y4 = 500 + (int) (Math.sin(angle));

    g.setColor(Color.BLACK);
    g.fillOval(x1 + OFFSET, y1 + OFFSET, 5, 5);
    g.fillOval(x2 + OFFSET, y2 + OFFSET, 5, 5);
    g.fillOval(x3 + OFFSET, y3 + OFFSET, 5, 5);
    g.fillOval(x4 + OFFSET, y4 + OFFSET, 5, 5);


  }
}
