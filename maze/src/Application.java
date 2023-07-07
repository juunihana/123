import java.util.Random;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Application {

  public static int[][] initMap() {
    int[][] map = new int[256][256];

    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        if (i == 0 || j == 0 || i == map.length - 1 || j == map[i].length - 1) {
          map[i][j] = 0;
        }
      }
    }

    for (int i = 1; i < map.length - 1; i++) {
      for (int j = 1; j < map[i].length - 1; j++) {
        map[i][j] = new Random().nextInt(3);

//          map[i][j] = new Random().nextInt(2);
      }
    }

    return map;
  }

  public static void main(String[] args) throws InterruptedException {
    JFrame frame = new JFrame("Maze");
    MazePanel panel = new MazePanel(initMap());
    panel.setSize(768, 768);
    frame.setSize(1024, 1024);
    frame.setLayout(null);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.add(panel);
    frame.setVisible(true);

    while (true) {
      panel.setMap(initMap());
      panel.repaint();

      Thread.sleep(1);
    }
  }
}
